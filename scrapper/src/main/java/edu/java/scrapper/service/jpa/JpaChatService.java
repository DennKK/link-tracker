package edu.java.scrapper.service.jpa;

import edu.java.scrapper.domain.entity.ChatEntity;
import edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import edu.java.scrapper.service.ChatService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatRepository jpaChatRepository;

    @Override
    public void register(long tgChatId) {
        ChatEntity chat = new ChatEntity();
        chat.setTgChatId(tgChatId);
        chat.setRegisteredAt(OffsetDateTime.now());
        jpaChatRepository.save(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        ChatEntity chat =
            jpaChatRepository.findByTgChatId(tgChatId).orElseThrow(() -> new RuntimeException("Chat not found!"));
        jpaChatRepository.delete(chat);
    }
}
