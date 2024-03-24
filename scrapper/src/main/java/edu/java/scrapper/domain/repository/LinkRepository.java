package edu.java.scrapper.domain.repository;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import java.util.Collection;
import java.util.List;

public interface LinkRepository {
    Iterable<LinkDto> findAll();

    void add(LinkDto link);

    int remove(LinkDto link);

    void map(LinkDto link, ChatDto chat);

    void unmap(LinkDto link, ChatDto chat);

    List<LinkDto> findAllByChat(ChatDto chatDto);

    LinkDto getByUrl(String url);

    Collection<LinkDto> findOlderThan(int minutes);

    void update(LinkDto link);

    List<ChatDto> getChats(LinkDto link);
}
