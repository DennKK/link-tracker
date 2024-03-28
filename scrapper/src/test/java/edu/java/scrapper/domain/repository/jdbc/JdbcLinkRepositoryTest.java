package edu.java.scrapper.domain.repository.jdbc;

import edu.java.scrapper.IntegrationEnvironment;
import edu.java.scrapper.domain.dto.LinkDto;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
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
        links.add(new LinkDto(null, "stackoferflow.com", OffsetDateTime.now()));
        links.add(new LinkDto(null, "github.com", OffsetDateTime.now()));

        for (LinkDto link : links) {
            linkRepository.add(link);
        }

        List<LinkDto> linksFromDB = (List<LinkDto>) linkRepository.findAll();
        Assertions.assertEquals(links.size(), linksFromDB.size());
        for (int i = 0; i < links.size(); i++) {
            long difference =
                ChronoUnit.SECONDS.between(links.get(i).getUpdatedAt(), linksFromDB.get(i).getUpdatedAt());
            Assertions.assertTrue(Math.abs(difference) < 5); // Acceptable difference of less than 5 seconds
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
            OffsetDateTime.now()
        );
        linkRepository.add(link);
        Assertions.assertEquals(0, linkRepository.remove(link));
    }

    @Test
    @Rollback
    @Transactional
    void isIdExistsTest() {
        String url = "stackoferflow.com";
        LinkDto link = new LinkDto(
            null,
            url,
            OffsetDateTime.now()
        );
        linkRepository.add(link);
        LinkDto linkFromDb = linkRepository.getByUrl(url);
        Assertions.assertNotNull(linkFromDb.getLinkId(), "LinkId should not be null");
        Assertions.assertTrue(linkFromDb.getLinkId() > 0, "LinkId should be positive");
    }
}
