package edu.java.scrapper.domain.repository.jooq;

import edu.java.scrapper.domain.dto.ChatDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import static edu.java.scrapper.domain.jooq.Tables.CHATS;
import static edu.java.scrapper.domain.jooq.Tables.LINKS;

@RequiredArgsConstructor
public class JooqChatRepository {
    private final DSLContext dslContext;

    public void add(ChatDto chat) {
        dslContext.insertInto(CHATS)
            .set(CHATS.REGISTERED_AT, chat.getRegisteredAt()).execute();
    }

    public List<ChatDto> findAll() {
        return dslContext.selectFrom(CHATS).fetchInto(ChatDto.class);
    }

    public int remove(ChatDto chat) {
        return dslContext.delete(CHATS)
            .where(LINKS.LINK_ID.eq(chat.getChatId().intValue())).execute();
    }
}
