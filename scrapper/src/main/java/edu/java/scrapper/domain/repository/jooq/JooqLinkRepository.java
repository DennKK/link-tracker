package edu.java.scrapper.domain.repository.jooq;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.domain.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.domain.generated.Tables.CHATS;
import static edu.java.scrapper.domain.generated.Tables.LINKS;
import static edu.java.scrapper.domain.generated.Tables.LINKS_TO_CHATS;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository implements LinkRepository {
    private final DSLContext dslContext;

    @Override
    public Iterable<LinkDto> findAll() {
        return dslContext.selectFrom(LINKS)
            .fetchInto(LinkDto.class);
    }

    @Override
    public void add(LinkDto link) {
        dslContext.insertInto(LINKS)
            .set(LINKS.URL, link.getUrl())
            .set(LINKS.CHECKED_AT, link.getCheckedAt())
            .set(LINKS.UPDATED_AT, link.getUpdatedAt())
            .execute();
    }

    @Override
    public int remove(LinkDto link) {
        return dslContext.delete(LINKS)
            .where(LINKS.LINK_ID.eq(link.getLinkId()))
            .execute();
    }

    @Override
    public void map(Long linkId, Long chatId) {
        dslContext.insertInto(LINKS_TO_CHATS)
            .set(LINKS_TO_CHATS.LINK_ID, linkId)
            .set(LINKS_TO_CHATS.CHAT_ID, chatId)
            .execute();
    }

    @Override
    public void unmap(Long linkId, Long chatId) {
        dslContext.delete(LINKS_TO_CHATS)
            .where(LINKS_TO_CHATS.LINK_ID.eq(linkId))
            .and(LINKS_TO_CHATS.CHAT_ID.eq(chatId))
            .execute();
    }

    @Override
    public void updateLastCheckTime(LinkDto link) {
        dslContext.update(LINKS)
            .set(LINKS.CHECKED_AT, link.getCheckedAt())
            .where(LINKS.LINK_ID.eq(link.getLinkId()))
            .execute();
    }

    @Override
    public void refreshLinkActivity(LinkDto link) {
        dslContext.update(LINKS)
            .set(LINKS.UPDATED_AT, link.getUpdatedAt())
            .where(LINKS.LINK_ID.eq(link.getLinkId()))
            .execute();
    }

    @Override
    public Collection<ChatDto> getChats(LinkDto link) {
        return dslContext.select()
            .from(CHATS)
            .join(LINKS_TO_CHATS).on(CHATS.CHAT_ID.eq(LINKS_TO_CHATS.CHAT_ID))
            .where(LINKS_TO_CHATS.LINK_ID.eq(link.getLinkId()))
            .fetchInto(ChatDto.class);
    }

    @Override
    public Collection<LinkDto> findLinksNotCheckedSince(int minutes) {
        OffsetDateTime thresholdTime = OffsetDateTime.now().minusMinutes(minutes);

        return dslContext.selectFrom(LINKS)
            .where(LINKS.CHECKED_AT.lessThan(thresholdTime))
            .fetchInto(LinkDto.class);
    }

    @Override
    public Collection<LinkDto> findAllByChat(ChatDto chatDto) {
        Result<Record> result = dslContext.select()
            .from(LINKS)
            .join(LINKS_TO_CHATS).on(LINKS.LINK_ID.eq(LINKS_TO_CHATS.LINK_ID))
            .join(CHATS).on(LINKS_TO_CHATS.CHAT_ID.eq(CHATS.CHAT_ID))
            .where(CHATS.CHAT_ID.eq(chatDto.getChatId()))
            .fetch();

        return result.map(resultRecord -> {
            Long linkId = resultRecord.get(LINKS.LINK_ID);
            String url = resultRecord.get(LINKS.URL);
            OffsetDateTime checkedAt = resultRecord.get(LINKS.CHECKED_AT);
            OffsetDateTime updatedAt = resultRecord.get(LINKS.UPDATED_AT);
            return new LinkDto(linkId, url, checkedAt, updatedAt);
        });
    }

    @Override
    public LinkDto getByUrl(String url) {
        return dslContext.selectFrom(LINKS)
            .where(LINKS.URL.eq(url))
            .fetchOneInto(LinkDto.class);
    }
}
