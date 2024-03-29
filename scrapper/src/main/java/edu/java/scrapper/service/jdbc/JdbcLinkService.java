package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.domain.repository.jdbc.JdbcChatRepository;
import edu.java.scrapper.domain.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepository jdbcLinkRepository;
    private final JdbcChatRepository jdbcChatRepository;

    @Override
    public LinkDto add(long tgChatId, URI url) {
        LinkDto link = new LinkDto();
        link.setUrl(url.toString());
        link.setCheckedAt(OffsetDateTime.now());
        link.setUpdatedAt(OffsetDateTime.now());
        jdbcLinkRepository.add(link);
        LinkDto linkFromDb = jdbcLinkRepository.getByUrl(link.getUrl());
        ChatDto chatFromDb = jdbcChatRepository.findByTgChatId(tgChatId);
        jdbcLinkRepository.map(linkFromDb.getLinkId(), chatFromDb.getChatId());
        return link;
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        LinkDto link = jdbcLinkRepository.getByUrl(url.toString());
        ChatDto chat = jdbcChatRepository.findByTgChatId(tgChatId);
        jdbcLinkRepository.unmap(link.getLinkId(), chat.getChatId());
        return link;
    }

    @Override
    public Collection<LinkDto> listAll(long tgChatId) {
        ChatDto chat = jdbcChatRepository.findByTgChatId(tgChatId);
        return jdbcLinkRepository.findAllByChat(chat);
    }

    @Override
    public Collection<ChatDto> getChatsForLink(LinkDto link) {
        return jdbcLinkRepository.getChats(link);
    }

    @Override
    public Collection<LinkDto> getOlderThan(int minutes) {
        return jdbcLinkRepository.findLinksNotCheckedSince(minutes);
    }

    @Override
    public void updateLastCheckTime(LinkDto link) {
        jdbcLinkRepository.updateLastCheckTime(link);
    }

    @Override
    public void refreshLinkActivity(LinkDto link) {
        jdbcLinkRepository.refreshLinkActivity(link);
    }
}
