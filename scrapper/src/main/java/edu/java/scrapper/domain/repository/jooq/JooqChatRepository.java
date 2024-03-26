package edu.java.scrapper.domain.repository.jooq;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.repository.ChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.domain.jooq_generated.Tables.CHATS;

@Repository
@RequiredArgsConstructor
public class JooqChatRepository implements ChatRepository {
    private final DSLContext dslContext;

    @Override
    public void add(ChatDto chat) {
        dslContext.insertInto(CHATS)
            .set(CHATS.REGISTERED_AT, chat.getRegisteredAt()).execute();
    }

    @Override
    public List<ChatDto> findAll() {
        return dslContext.select().from(CHATS).fetchInto(ChatDto.class);
    }

    @Override
    public int remove(ChatDto chat) {
        return dslContext.delete(CHATS)
            .where(CHATS.CHAT_ID.eq(chat.getChatId())).execute();
    }
}
