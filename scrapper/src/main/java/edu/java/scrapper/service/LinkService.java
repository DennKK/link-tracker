package edu.java.scrapper.service;

import edu.java.scrapper.domain.dto.LinkDto;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    LinkDto add(long tgChatId, URI url);

    LinkDto remove(long tgChatId, URI url);

    Collection<LinkDto> listAll(long tgChatId);
}