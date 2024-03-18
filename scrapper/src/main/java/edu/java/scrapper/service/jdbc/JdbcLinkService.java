package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.domain.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepository jdbcLinkRepository;

    @Override
    public LinkDto add(long tgChatId, URI url) {
        LinkDto link = new LinkDto();
        link.setUrl(url.toString());
        link.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        jdbcLinkRepository.add(link);
        return link;
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        LinkDto link = jdbcLinkRepository.getByUrl(url.toString());
        jdbcLinkRepository.unmap(link, new ChatDto(tgChatId, null));
        return link;
    }

    @Override
    public Collection<LinkDto> listAll(long tgChatId) {
        return jdbcLinkRepository.findAllByChat(new ChatDto(tgChatId, null));
    }
}