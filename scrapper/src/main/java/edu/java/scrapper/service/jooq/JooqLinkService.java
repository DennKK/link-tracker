package edu.java.scrapper.service.jooq;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.domain.repository.jooq.JooqChatRepository;
import edu.java.scrapper.domain.repository.jooq.JooqLinkRepository;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.factory.LinkFactory;
import java.net.URI;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqLinkRepository jooqLinkRepository;
    private final JooqChatRepository jooqChatRepository;

    @Override
    public LinkDto add(long tgChatId, URI url) {
        LinkDto link = LinkFactory.createLinkDto(url);
        jooqLinkRepository.add(link);
        LinkDto linkFromDb = jooqLinkRepository.getByUrl(link.getUrl());
        ChatDto chatFromDb = jooqChatRepository.findByTgChatId(tgChatId);
        jooqLinkRepository.map(linkFromDb.getLinkId(), chatFromDb.getChatId());
        return link;
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        LinkDto link = jooqLinkRepository.getByUrl(url.toString());
        ChatDto chat = jooqChatRepository.findByTgChatId(tgChatId);
        jooqLinkRepository.unmap(link.getLinkId(), chat.getChatId());
        return link;
    }

    @Override
    public Collection<LinkDto> listAll(long tgChatId) {
        ChatDto chat = jooqChatRepository.findByTgChatId(tgChatId);
        return jooqLinkRepository.findAllByChat(chat);
    }

    @Override
    public Collection<ChatDto> getChatsForLink(LinkDto link) {
        return jooqLinkRepository.getChats(link);
    }

    @Override
    public Collection<LinkDto> getOlderThan(int minutes) {
        return jooqLinkRepository.findLinksNotCheckedSince(minutes);
    }

    @Override
    public void updateLastCheckTime(LinkDto link) {
        jooqLinkRepository.updateLastCheckTime(link);
    }

    @Override
    public void refreshLinkActivity(LinkDto link) {
        jooqLinkRepository.refreshLinkActivity(link);
    }
}
