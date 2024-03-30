package edu.java.scrapper.domain.repository.jpa;

import edu.java.scrapper.IntegrationEnvironment;
import edu.java.scrapper.domain.entity.LinkEntity;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JpaLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JpaLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void findAndAddTest() {
        List<LinkEntity> links = new ArrayList<>();
        links.add(new LinkEntity(null, "https://vk.com", OffsetDateTime.now(), OffsetDateTime.now(), new HashSet<>()));
        links.add(new LinkEntity(
            null,
            "https://vk.com/feed",
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            new HashSet<>()
        ));

        linkRepository.saveAll(links);

        List<LinkEntity> linksFromDb = linkRepository.findAll();
        Assertions.assertEquals(links.size(), linksFromDb.size());
        for (int i = 0; i < links.size(); i++) {
            long difference =
                ChronoUnit.SECONDS.between(links.get(i).getUpdatedAt(), linksFromDb.get(i).getUpdatedAt());
            Assertions.assertTrue(Math.abs(difference) < 5); // Acceptable difference of less than 5 seconds
            Assertions.assertEquals(links.get(i).getUrl(), linksFromDb.get(i).getUrl());
        }
    }

    @Test
    @Transactional
    @Rollback
    void findLinksNotCheckedSinceTest() {
        int minutes = 10;
        int expectedCollectionSize = 1;
        String expectedUrl = "https://vk.com";
        OffsetDateTime expectedLastCheckTime =
            OffsetDateTime.parse("2000-03-29T17:00:04Z");

        List<LinkEntity> links = new ArrayList<>(
            List.of(
                new LinkEntity(
                    null,
                    "https://vk.com",
                    OffsetDateTime.parse("2000-03-29T17:00:04Z"),
                    OffsetDateTime.now(),
                    new HashSet<>()
                ),
                new LinkEntity(
                    null,
                    "https://vk.com/feed",
                    OffsetDateTime.now(),
                    OffsetDateTime.now(),
                    new HashSet<>()
                )
            )
        );

        linkRepository.saveAll(links);

        Collection<LinkEntity> oldLinks =
            linkRepository.findLinksNotCheckedSince(OffsetDateTime.now().minusMinutes(minutes));

        Assertions.assertEquals(expectedCollectionSize, oldLinks.size());
        for (LinkEntity link : oldLinks) {
            Assertions.assertEquals(expectedUrl, link.getUrl());
            Assertions.assertEquals(expectedLastCheckTime, link.getCheckedAt());
        }
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        LinkEntity link =
            new LinkEntity(null, "https://vk.com", OffsetDateTime.now(), OffsetDateTime.now(), new HashSet<>());

        linkRepository.save(link);
        List<LinkEntity> linksFromDb = linkRepository.findAll();
        Assertions.assertEquals(1, linksFromDb.size());
        linkRepository.delete(link);
        linksFromDb = linkRepository.findAll();
        Assertions.assertEquals(0, linksFromDb.size());
    }
}
