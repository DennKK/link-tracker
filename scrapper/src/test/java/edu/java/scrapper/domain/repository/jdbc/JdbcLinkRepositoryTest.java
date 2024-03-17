package edu.java.scrapper.domain.repository.jdbc;

import edu.java.scrapper.IntegrationEnvironment;
import edu.java.scrapper.domain.dto.LinkDto;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JdbcLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void findAndAddTest() {
        List<LinkDto> links = new ArrayList<>();
        links.add(new LinkDto(null, "stackoferflow.com", Timestamp.valueOf("2024-12-12 12:12:00")));
        links.add(new LinkDto(null, "github.com", Timestamp.valueOf("2024-12-12 12:12:00")));

        for (LinkDto link : links) {
            linkRepository.add(link);
        }

        List<LinkDto> linksFromDB = (List<LinkDto>) linkRepository.findAll();
        Assertions.assertEquals(links.size(), linksFromDB.size());
        for (int i = 0; i < links.size(); i++) {
            Assertions.assertEquals(links.get(i).getUrl(), linksFromDB.get(i).getUrl());
        }
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        LinkDto link = new LinkDto(
            null,
            "stackoferflow.com",
            Timestamp.valueOf("2024-12-12 12:12:00")
        );
        linkRepository.add(link);
        Assertions.assertEquals(0, linkRepository.remove(link));
    }
}
