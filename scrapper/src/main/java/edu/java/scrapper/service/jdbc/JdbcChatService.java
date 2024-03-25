package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.repository.jdbc.JdbcChatRepository;
import edu.java.scrapper.service.ChatService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcChatService implements ChatService {
    private final JdbcChatRepository jdbcChatRepository;

    @Override
    public void register(long tgChatId) {
        ChatDto chat = new ChatDto(tgChatId, OffsetDateTime.now());
        jdbcChatRepository.add(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        jdbcChatRepository.remove(new ChatDto(tgChatId, null));
    }
}
