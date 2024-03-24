package edu.java.scrapper.service.jpa;

import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.domain.entity.ChatEntity;
import edu.java.scrapper.domain.entity.LinkEntity;
import edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import edu.java.scrapper.service.LinkFactory;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;

    @Override
    public LinkDto add(long tgChatId, URI url) {
        LinkEntity link = jpaLinkRepository.findByUrl(url.toString()).orElse(LinkFactory.createLinkEntity(url));
        ChatEntity chat = jpaChatRepository.findById(tgChatId)
            .orElseThrow(() -> new RuntimeException("Chat with id " + tgChatId + "not found!"));

        chat.getLinks().add(link);
        link.getChats().add(chat);
        jpaChatRepository.save(chat);
        jpaLinkRepository.save(link);

        return LinkFactory.entityToDto(link);
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        LinkEntity link = jpaLinkRepository.findByUrl(url.toString()).orElse(LinkFactory.createLinkEntity(url));
        ChatEntity chat = jpaChatRepository.findById(tgChatId)
            .orElseThrow(() -> new RuntimeException("Chat with id " + tgChatId + "not found!"));

        chat.getLinks().remove(link);
        link.getChats().remove(chat);
        jpaChatRepository.save(chat);
        jpaLinkRepository.save(link);

        return LinkFactory.entityToDto(link);
    }

    @Override
    public Collection<LinkDto> listAll(long tgChatId) {
        ChatEntity chat = jpaChatRepository.findById(tgChatId)
            .orElseThrow(() -> new RuntimeException("Chat with id " + tgChatId + "not found!"));

        Collection<LinkDto> links = new HashSet<>();
        for (LinkEntity linkEntity : chat.getLinks()) {
            links.add(LinkFactory.entityToDto(linkEntity));
        }

        return links;
    }
}
