package edu.java.scrapper.service;

import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.domain.entity.LinkEntity;
import java.net.URI;
import java.time.OffsetDateTime;

public class LinkFactory {
    public static LinkDto createLinkDto(URI url) {
        LinkDto link = new LinkDto();
        link.setUrl(url.toString());
        link.setUpdatedAt(OffsetDateTime.now());
        return link;
    }

    public static LinkEntity createLinkEntity(URI url) {
        LinkEntity link = new LinkEntity();
        link.setUrl(url.toString());
        link.setUpdatedAt(OffsetDateTime.now());
        return link;
    }

    public static LinkDto entityToDto(LinkEntity linkEntity) {
        LinkDto linkDto = new LinkDto();
        linkDto.setLinkId(linkEntity.getLinkId());
        linkDto.setUrl(linkEntity.getUrl());
        linkDto.setUpdatedAt(linkEntity.getUpdatedAt());
        return linkDto;
    }
}
