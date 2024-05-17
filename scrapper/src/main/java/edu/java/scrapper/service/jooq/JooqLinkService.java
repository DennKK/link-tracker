package edu.java.scrapper.service.jooq;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.domain.repository.jooq.JooqLinkRepository;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqLinkRepository jooqLinkRepository;

    @Override
    public LinkDto add(long tgChatId, URI url) {
        LinkDto link = new LinkDto();
        link.setUrl(String.valueOf(url));
        link.setUpdatedAt(OffsetDateTime.now());
        jooqLinkRepository.add(link);
        jooqLinkRepository.map(link, new ChatDto(tgChatId, null));
        return link;
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        LinkDto link = jooqLinkRepository.getByUrl(url.toString());
        jooqLinkRepository.unmap(link, new ChatDto(tgChatId, null));
        return link;
    }

    @Override
    public Collection<LinkDto> listAll(long tgChatId) {
        return jooqLinkRepository.findAllByChat(new ChatDto(tgChatId, null));
    }
}
