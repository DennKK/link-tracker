package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.domain.repository.LinkRepository;
import edu.java.scrapper.domain.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.service.LinkUpdaterBase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcLinkUpdater extends LinkUpdaterBase {
    private final JdbcLinkRepository jdbcLinkRepository;

    @Override
    protected LinkRepository getLinkRepository() {
        return jdbcLinkRepository;
    }
}
