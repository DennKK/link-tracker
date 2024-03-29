package edu.java.scrapper.domain.repository;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import java.util.Collection;

public interface LinkRepository {
    Iterable<LinkDto> findAll();

    void add(LinkDto link);

    int remove(LinkDto link);

    void map(Long linkId, Long chatId);

    void unmap(Long linkId, Long chatId);

    Collection<LinkDto> findAllByChat(ChatDto chatDto);

    LinkDto getByUrl(String url);

    Collection<LinkDto> findLinksNotCheckedSince(int minutes);

    void updateLastCheckTime(LinkDto link);

    void refreshLinkActivity(LinkDto link);

    Collection<ChatDto> getChats(LinkDto link);
}
