package edu.java.scrapper.domain.repository.jooq;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.repository.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.generated.Tables.CHATS;

@Repository
@RequiredArgsConstructor
public class JooqChatRepository implements ChatRepository {
    private final DSLContext dslContext;

    @Override
    @Transactional
    public void add(ChatDto chat) {
        dslContext.insertInto(CHATS)
            .set(CHATS.TG_CHAT_ID, chat.getTgChatId())
            .set(CHATS.REGISTERED_AT, chat.getRegisteredAt())
            .execute();
    }

    @Override
    @Transactional
    public List<ChatDto> findAll() {
        return dslContext.select().from(CHATS).fetchInto(ChatDto.class);
    }

    @Override
    @Transactional
    public int remove(ChatDto chat) {
        return dslContext.delete(CHATS)
            .where(CHATS.TG_CHAT_ID.eq(chat.getTgChatId())).execute();
    }

    @Override
    @Transactional
    public ChatDto findByTgChatId(Long tgChatId) {
        return dslContext
            .selectFrom(CHATS)
            .where(CHATS.TG_CHAT_ID.eq(tgChatId))
            .fetchOneInto(ChatDto.class);
    }
}
