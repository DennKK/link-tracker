package edu.java.scrapper.domain.repository.jdbc;

import edu.java.scrapper.IntegrationEnvironment;
import edu.java.scrapper.domain.dto.ChatDto;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcChatRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JdbcChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void findAndAddTest() {
        List<ChatDto> chats = new ArrayList<>();
        chats.add(new ChatDto(null, 1L, OffsetDateTime.now()));
        chats.add(new ChatDto(null, 2L, OffsetDateTime.now()));

        for (ChatDto chat : chats) {
            chatRepository.add(chat);
        }

        List<ChatDto> chatsFromDB = (List<ChatDto>) chatRepository.findAll();
        Assertions.assertEquals(chats.size(), chatsFromDB.size());
        for (int i = 0; i < chats.size(); i++) {
            long difference =
                ChronoUnit.SECONDS.between(chats.get(i).getRegisteredAt(), chatsFromDB.get(i).getRegisteredAt());
            Assertions.assertTrue(Math.abs(difference) < 5); // Acceptable difference of less than 5 seconds
        }
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        ChatDto chat = new ChatDto(
            null,
            1L,
            OffsetDateTime.now()
        );

        chatRepository.add(chat);
        List<ChatDto> chats = (List<ChatDto>) chatRepository.findAll();
        Assertions.assertEquals(1, chats.size());

        chatRepository.remove(chat);
        chats = (List<ChatDto>) chatRepository.findAll();
        Assertions.assertEquals(0, chats.size());
    }

    @Test
    @Rollback
    @Transactional
    void isIdExistsTest() {
        ChatDto chat = new ChatDto(
            null,
            1L,
            OffsetDateTime.now()
        );
        chatRepository.add(chat);
        List<ChatDto> chatFromDb = (List<ChatDto>) chatRepository.findAll();
        Assertions.assertNotNull(chatFromDb.getFirst().getChatId(), "LinkId should not be null");
        Assertions.assertTrue(chatFromDb.getFirst().getChatId() > 0, "LinkId should be positive");
    }
}
