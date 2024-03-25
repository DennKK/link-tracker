package edu.java.scheduler;

import edu.java.client.github.GitHubClient;
import edu.java.client.tgbot.BotClient;
import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.service.LinkService;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler implements LinkUpdater {
    private static final Logger LOGGER = Logger.getLogger(LinkUpdaterScheduler.class.getName());
    private final GitHubClient gitHubClient;
    @Value("${app.update-frequency}")
    private int updateFrequency;
    private final LinkService linkService;
    private final BotClient botClient;

    @Override
    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        LOGGER.info("Running link update task...");

        Collection<LinkDto> oldLinks = linkService.getOlderThan(updateFrequency);
        for (LinkDto link : oldLinks) {
            OffsetDateTime lastTimeUpdated = getLastUpdateTime(link);
            if (lastTimeUpdated.isAfter(link.getUpdatedAt())) {
                Collection<ChatDto> chats = linkService.getChatsForLink(link);
                List<Long> chatIds = chats.stream().map(ChatDto::getChatId).collect(Collectors.toList());
                botClient.sendUpdateToBot(link, chatIds);
                link.setUpdatedAt(lastTimeUpdated);
                linkService.updateLink(link);
            }
        }
    }

    // TODO: Create url parser
    // В предыдущих заданиях нигде не было необходимости парсить ссылки
    // Хотелось бы отличать ссылку на stackoverflow и github друга от друга и от других ссылок
    // Распарсиватель ссылок пока не готов, поэтому оставляю заглушку
    private OffsetDateTime getLastUpdateTime(LinkDto link) {
        return OffsetDateTime.parse(gitHubClient.getGitHubResponse("DennKK", "link-tracker").updatedAt());
    }
}
