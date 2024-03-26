package edu.java.scrapper.domain.repository.jooq;

import edu.java.scrapper.IntegrationEnvironment;
import edu.java.scrapper.domain.dto.LinkDto;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JooqLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JooqLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void findAndAddTest() {
        List<LinkDto> links = new ArrayList<>();
        links.add(new LinkDto(null, "stackoferflow.com", OffsetDateTime.now()));
        links.add(new LinkDto(null, "github.com", OffsetDateTime.now()));

        for (LinkDto l : links) {
            linkRepository.add(l);
        }

        List<LinkDto> linksFromDb = (List<LinkDto>) linkRepository.findAll();
        Assertions.assertEquals(links.size(), linksFromDb.size());
        for (int i = 0; i < links.size(); i++) {
            Assertions.assertEquals(links.get(i).getUrl(), linksFromDb.get(i).getUrl());
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
}
