package edu.java.processor;

import edu.java.scrapper.domain.dto.LinkDto;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;

@Component
public class GitHubLinkUpdateProcessor implements LinkUpdateProcessor{
    @Override
    public boolean supports(String url) {
        return false;
    }

    @Override
    public OffsetDateTime getLastActivityTime(String url) {
        return null;
    }

    @Override
    public void processLinkUpdate(LinkDto linkDto) {

    }
}
