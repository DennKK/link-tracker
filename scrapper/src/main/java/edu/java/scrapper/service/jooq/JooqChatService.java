package edu.java.scrapper.service.jooq;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.repository.jooq.JooqChatRepository;
import edu.java.scrapper.service.ChatService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqChatService implements ChatService {
    private final JooqChatRepository jooqChatRepository;

    @Override
    public void register(long tgChatId) {
        jooqChatRepository.add(new ChatDto(null, tgChatId, OffsetDateTime.now()));
    }

    @Override
    public void unregister(long tgChatId) {
        jooqChatRepository.remove(new ChatDto(null, tgChatId, null));
    }
}
