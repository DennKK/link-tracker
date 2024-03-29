package edu.java.processor;

import edu.java.scrapper.domain.dto.LinkDto;
import java.time.OffsetDateTime;

public interface LinkUpdateProcessor {
    boolean supports(String url);

    OffsetDateTime getLastActivityTime(String url);

    void processLinkUpdate(LinkDto linkDto);
}
