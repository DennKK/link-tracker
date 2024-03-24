package edu.java.scrapper.service.jooq;

import edu.java.scrapper.domain.repository.LinkRepository;
import edu.java.scrapper.domain.repository.jooq.JooqLinkRepository;
import edu.java.scrapper.service.LinkUpdaterBase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JooqLinkUpdater extends LinkUpdaterBase {
    private final JooqLinkRepository jooqLinkRepository;

    @Override
    protected LinkRepository getLinkRepository() {
        return jooqLinkRepository;
    }
}
