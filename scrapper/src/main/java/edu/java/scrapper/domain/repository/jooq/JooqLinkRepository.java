package edu.java.scrapper.domain.repository.jooq;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.domain.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.domain.jooq_generated.Tables.CHATS;
import static edu.java.scrapper.domain.jooq_generated.Tables.LINKS;
import static edu.java.scrapper.domain.jooq_generated.Tables.LINKS_TO_CHATS;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository implements LinkRepository {
    private final DSLContext dslContext;

    @Override
    public void add(LinkDto link) {
        dslContext.insertInto(LINKS)
            .set(LINKS.URL, link.getUrl())
            .set(LINKS.UPDATED_AT, link.getUpdatedAt()).execute();
    }

    @Override
    public void update(LinkDto link) {
        dslContext.update(LINKS)
            .set(LINKS.URL, link.getUrl())
            .set(LINKS.UPDATED_AT, link.getUpdatedAt())
            .where(LINKS.LINK_ID.eq(link.getLinkId())).execute();

    }

    @Override
    public List<ChatDto> getChats(LinkDto link) {
        return dslContext.select()
            .from(CHATS)
            .join(LINKS_TO_CHATS).on(CHATS.CHAT_ID.eq(LINKS_TO_CHATS.CHAT_ID))
            .where(LINKS_TO_CHATS.LINK_ID.eq(link.getLinkId()))
            .fetchInto(ChatDto.class);
    }

    @Override
    public Collection<LinkDto> findOlderThan(int minutes) {
        OffsetDateTime thresholdTime = OffsetDateTime.now().minusMinutes(minutes);

        return dslContext.selectFrom(LINKS)
            .where(LINKS.UPDATED_AT.lessThan(thresholdTime))
            .fetchInto(LinkDto.class);
    }

    @Override
    public int remove(LinkDto link) {
        return dslContext.delete(LINKS)
            .where(LINKS.LINK_ID.eq(link.getLinkId())).execute();
    }

    @Override
    public void map(LinkDto link, ChatDto chat) {
        dslContext.insertInto(LINKS_TO_CHATS)
            .set(LINKS.LINK_ID, link.getLinkId())
            .set(CHATS.CHAT_ID, chat.getChatId()).execute();
    }

    @Override
    public void unmap(LinkDto link, ChatDto chat) {
        dslContext.delete(LINKS_TO_CHATS)
            .where(LINKS.LINK_ID.eq(link.getLinkId()))
            .and(CHATS.CHAT_ID.eq(chat.getChatId())).execute();
    }

    @Override
    public List<LinkDto> findAllByChat(ChatDto chatDto) {
        String linkIdFromLinks = "links.link_id";
        String chatIdFromChat = "chats.chat_id";

        Result<Record> result = dslContext.select()
            .from(table("links")
                .join(table("links_to_chats")).on(field(linkIdFromLinks).eq(field("links_to_chats.link_id")))
                .join(table("chats")).on(field("links_to_chats.chat_id").eq(field(chatIdFromChat))))
            .where(field(chatIdFromChat).eq(chatDto.getChatId()))
            .fetch();

        return result.map(resultRecord -> {
            Long linkId = resultRecord.get(linkIdFromLinks, Long.class);
            String url = resultRecord.get("links.url", String.class);
            OffsetDateTime updatedAt = resultRecord.get("links.updated_at", OffsetDateTime.class);
            return new LinkDto(linkId, url, updatedAt);
        });
    }

    @Override
    public LinkDto getByUrl(String url) {
        return dslContext.selectFrom(LINKS)
            .where(LINKS.URL.eq(url)).fetchOneInto(LinkDto.class);
    }

    @Override
    public List<LinkDto> findAll() {
        return dslContext.selectFrom(LINKS).fetchInto(LinkDto.class);
    }
}
