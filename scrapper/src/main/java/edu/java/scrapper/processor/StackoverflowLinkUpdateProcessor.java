package edu.java.scrapper.processor;

import edu.java.scrapper.domain.dto.LinkDto;

public class StackoverflowLinkUpdateProcessor implements LinkUpdateProcessor {
    @Override
    public boolean supports(String url) {
        return false;
    }

    @Override
    public void processLinkUpdate(LinkDto linkDto) {

    }
}
