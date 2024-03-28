package edu.java.scrapper.controller;

import edu.java.payload.dto.request.AddLinkRequest;
import edu.java.payload.dto.request.RemoveLinkRequest;
import edu.java.payload.dto.response.LinkResponse;
import edu.java.payload.dto.response.ListLinksResponse;
import edu.java.scrapper.controller.api.LinkApi;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.service.LinkService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LinksController implements LinkApi {
    private final LinkService service;

    @Override
    public ListLinksResponse linksGet(Long tgChatId) {
        log.info("Links get request for chat {}", tgChatId);
        List<LinkResponse> linkResponses = new ArrayList<>();

        for (LinkDto link : service.listAll(tgChatId)) {
            linkResponses.add(new LinkResponse(link.getLinkId(), URI.create(link.getUrl())));
        }

        return new ListLinksResponse(linkResponses, linkResponses.size());
    }

    @Override
    public LinkResponse linksPost(Long tgChatId, AddLinkRequest body) {
        log.info("Add link request link: {} to chat id: {}", body.link(), tgChatId);
        service.add(tgChatId, URI.create(body.link()));
        return new LinkResponse(tgChatId, URI.create(body.link()));
    }

    @Override
    public LinkResponse linksDelete(Long tgChatId, RemoveLinkRequest body) {
        log.info("Remove link request {} from chat id: {}", body.link(), tgChatId);
        service.remove(tgChatId, URI.create(body.link()));
        return new LinkResponse(tgChatId, URI.create(body.link()));
    }
}
