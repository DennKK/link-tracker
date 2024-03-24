package edu.java.scrapper.domain.repository;

import edu.java.scrapper.domain.dto.ChatDto;

public interface ChatRepository {
    void add(ChatDto chat);

    Iterable<ChatDto> findAll();

    int remove(ChatDto chat);
}
