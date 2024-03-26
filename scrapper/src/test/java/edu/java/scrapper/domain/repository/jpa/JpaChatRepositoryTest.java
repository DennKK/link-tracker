package edu.java.scrapper.domain.repository.jpa;

import edu.java.scrapper.IntegrationEnvironment;
import edu.java.scrapper.domain.entity.ChatEntity;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JpaChatRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JpaChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void findAndAddTest() {
        List<ChatEntity> chats = new ArrayList<>();
        chats.add(new ChatEntity(null, OffsetDateTime.now(), new HashSet<>()));
        chats.add(new ChatEntity(null, OffsetDateTime.now(), new HashSet<>()));

        for (ChatEntity chat : chats) {
            chatRepository.save(chat);
        }

        List<ChatEntity> chatsFromDb = chatRepository.findAll();
        Assertions.assertEquals(chats.size(), chatsFromDb.size());
        for (int i = 0; i < chats.size(); i++) {
            long difference =
                ChronoUnit.SECONDS.between(chats.get(i).getRegisteredAt(), chatsFromDb.get(i).getRegisteredAt());
            Assertions.assertTrue(Math.abs(difference) < 5); // Acceptable difference of less than 5 seconds
        }
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        ChatEntity chat = new ChatEntity(
            null,
            OffsetDateTime.now(),
            new HashSet<>()
        );

        chatRepository.save(chat);
        List<ChatEntity> chatsFromDb = chatRepository.findAll();
        Assertions.assertEquals(1, chatsFromDb.size());
        chatRepository.delete(chat);
        chatsFromDb = chatRepository.findAll();
        Assertions.assertEquals(0, chatsFromDb.size());
    }
}
