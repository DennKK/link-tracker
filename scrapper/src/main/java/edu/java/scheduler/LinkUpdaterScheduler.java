package edu.java.scheduler;

import edu.java.client.github.GitHubClient;
import edu.java.client.tgbot.BotClient;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.service.jdbc.JdbcLinkService;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class LinkUpdaterScheduler implements LinkUpdater {
    private final JdbcLinkService jdbcLinkService;
    private final GitHubClient gitHubClient;
    private final BotClient botClient;

    private int updateFrequency = 30;

    private static final Logger LOGGER = Logger.getLogger(LinkUpdaterScheduler.class.getName());

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        LOGGER.info("Running link update task...");

        Collection<LinkDto> oldLinks = jdbcLinkService.getOldLinks(updateFrequency);

        for (LinkDto link : oldLinks) {
            OffsetDateTime lastTimeUpdated = getLastUpdateTime(link);
            if (lastTimeUpdated.isAfter(link.getUpdatedAt())) {
                botClient.sendUpdateToBot(link);
                link.setUpdatedAt(lastTimeUpdated);
                jdbcLinkService.updateLink(link);
            }
        }
    }

    // TODO: url parser
    // В предыдущих заданиях нигде не было необходимости парсить ссылки
    // Хотелось бы отличать ссылку на stackoverflow и github друга от друга и от других ссылок
    // Распарсиватель ссылок пока не готов, поэтому оставляю заглушку
    private OffsetDateTime getLastUpdateTime(LinkDto link) {
        return OffsetDateTime.parse(gitHubClient.getGitHubResponse("DennKK", "link-tracker").updatedAt());
    }
}
