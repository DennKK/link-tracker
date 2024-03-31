package edu.java.scrapper.processor;

import edu.java.scrapper.domain.dto.LinkDto;

public interface LinkUpdateProcessor {
    boolean supports(String url);

    void processLinkUpdate(LinkDto link);
}
