package edu.java.scrapper.service.jpa;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.domain.entity.ChatEntity;
import edu.java.scrapper.domain.entity.LinkEntity;
import edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.factory.ChatFactory;
import edu.java.scrapper.service.factory.LinkFactory;
import jakarta.transaction.Transactional;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;
    private static final String CHAT_NOT_FOUND_TEMPLATE = "Chat with id %d not found!";
    private static final String LINK_NOT_FOUND_TEMPLATE = "Link not found!";

    @Override
    public LinkDto add(long tgChatId, URI url) {
        LinkEntity link = jpaLinkRepository.findByUrl(url.toString()).orElse(LinkFactory.createLinkEntity(url));
        ChatEntity chat = jpaChatRepository.findByTgChatId(tgChatId)
            .orElseThrow(() -> new RuntimeException(String.format(CHAT_NOT_FOUND_TEMPLATE, tgChatId)));

        chat.getLinks().add(link);
        link.getChats().add(chat);
        jpaChatRepository.save(chat);

        return LinkFactory.entityToDto(link);
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        LinkEntity link = jpaLinkRepository.findByUrl(url.toString()).orElse(LinkFactory.createLinkEntity(url));
        ChatEntity chat = jpaChatRepository.findByTgChatId(tgChatId)
            .orElseThrow(() -> new RuntimeException(String.format(CHAT_NOT_FOUND_TEMPLATE, tgChatId)));

        chat.getLinks().remove(link);
        link.getChats().remove(chat);
        jpaChatRepository.save(chat);

        return LinkFactory.entityToDto(link);
    }

    @Override
    public Collection<LinkDto> listAll(long tgChatId) {
        ChatEntity chat = jpaChatRepository.findByTgChatId(tgChatId)
            .orElseThrow(() -> new RuntimeException(String.format(CHAT_NOT_FOUND_TEMPLATE, tgChatId)));

        Collection<LinkDto> links = new HashSet<>();
        for (LinkEntity linkEntity : chat.getLinks()) {
            links.add(LinkFactory.entityToDto(linkEntity));
        }

        return links;
    }

    @Override
    public Collection<LinkDto> getOlderThan(int minutes) {
        Collection<LinkEntity> linkEntities =
            jpaLinkRepository.findLinksNotCheckedSince(OffsetDateTime.now().minusMinutes(minutes));
        return linkEntities.stream()
            .map(LinkFactory::entityToDto)
            .collect(Collectors.toList());
    }

    @Override
    public void updateLastCheckTime(LinkDto linkDto) {
        LinkEntity linkEntity = jpaLinkRepository.findById(linkDto.getLinkId())
            .orElseThrow(() -> new RuntimeException(LINK_NOT_FOUND_TEMPLATE));

        linkEntity.setCheckedAt(linkDto.getCheckedAt());

        jpaLinkRepository.save(linkEntity);
    }

    @Override
    public void refreshLinkActivity(LinkDto linkDto) {
        LinkEntity linkEntity = jpaLinkRepository.findById(linkDto.getLinkId())
            .orElseThrow(() -> new RuntimeException(LINK_NOT_FOUND_TEMPLATE));

        linkEntity.setUpdatedAt(linkDto.getUpdatedAt());

        jpaLinkRepository.save(linkEntity);
    }

    @Transactional
    @Override
    public Collection<ChatDto> getChatsForLink(LinkDto link) {
        LinkEntity linkEntity =
            jpaLinkRepository.findById(link.getLinkId())
                .orElseThrow(() -> new RuntimeException(LINK_NOT_FOUND_TEMPLATE));

        Collection<ChatDto> chats = new HashSet<>();
        for (ChatEntity chatEntity : linkEntity.getChats()) {
            chats.add(ChatFactory.entityToDto(chatEntity));
        }
        return chats;
    }
}
