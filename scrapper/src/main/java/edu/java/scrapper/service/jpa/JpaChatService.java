package edu.java.scrapper.service.jpa;

import edu.java.scrapper.domain.entity.ChatEntity;
import edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import edu.java.scrapper.service.ChatService;
import lombok.RequiredArgsConstructor;
import java.time.OffsetDateTime;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatRepository jpaChatRepository;

    @Override
    public void register(long tgChatId) {
        ChatEntity chat = new ChatEntity();
        chat.setChatId(tgChatId);
        chat.setRegisteredAt(OffsetDateTime.now());
        jpaChatRepository.save(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        ChatEntity chat = new ChatEntity();
        chat.setChatId(tgChatId);
        jpaChatRepository.delete(chat);
    }
}
