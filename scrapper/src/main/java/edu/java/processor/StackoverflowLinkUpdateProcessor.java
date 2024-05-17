package edu.java.processor;

import edu.java.scrapper.domain.dto.LinkDto;
import org.springframework.stereotype.Component;

@Component
public class StackoverflowLinkUpdateProcessor implements LinkUpdateProcessor {
    @Override
    public boolean supports(String url) {
        return false;
    }

    @Override
    public void processLinkUpdate(LinkDto linkDto) {

    }
}
