package edu.java.scheduler;

import edu.java.client.github.GitHubClient;
import edu.java.client.tgbot.BotClient;
import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.service.LinkService;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdaterScheduler implements LinkUpdater {
    private final GitHubClient gitHubClient;
    @Value("${app.update-frequency:1}")
    private int updateFrequency;
    private final LinkService linkService;
    private final BotClient botClient;

    @Override
    @Scheduled(fixedRateString = "${app.scheduler.interval}", fixedDelayString = "${app.scheduler.force-check-delay}")
    public void update() {
        try {
            log.info("Начало выполнения задачи обновления ссылок...");
            Collection<LinkDto> oldLinks = linkService.getOlderThan(updateFrequency);
            log.info("Найдено {} ссылок для обновления.", oldLinks.size());

            for (LinkDto link : oldLinks) {
                OffsetDateTime lastTimeUpdated = getLastUpdateTime(link);
                if (lastTimeUpdated.isAfter(link.getUpdatedAt())) {
                    Collection<ChatDto> chats = linkService.getChatsForLink(link);
                    List<Long> tgChatIds = chats.stream().map(ChatDto::getTgChatId).collect(Collectors.toList());
                    botClient.sendUpdateToBot(link, tgChatIds);
                    link.setUpdatedAt(lastTimeUpdated);
                    linkService.updateLink(link);
                }
            }

            log.info("Задача обновления ссылок успешно выполнена.");
        } catch (Exception e) {
            log.error("Ошибка при выполнении задачи обновления ссылок: {}", e.getMessage());
        }
        log.info("Метод завершил работу");
    }

    // TODO: Move the parser into a separate module
    // Временная функция для парсинг url
    private String[] parseGithubLink(String url) throws RuntimeException {
        String linkNotFoundTemplate = "Link does not match format %s {username}/{reponame}";
        String expectedPrefix = "https://github.com/";
        if (!url.startsWith(expectedPrefix)) {
            throw new RuntimeException(String.format(linkNotFoundTemplate, expectedPrefix));
        }

        String[] parts = url.substring(expectedPrefix.length()).split("/");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new RuntimeException(String.format(linkNotFoundTemplate, expectedPrefix));
        }

        return new String[] {parts[0], parts[1]};
    }

    private OffsetDateTime getLastUpdateTime(LinkDto link) {
        String[] parsedLink = parseGithubLink(link.getUrl());
        return OffsetDateTime.parse(gitHubClient.getGitHubResponse(parsedLink[0], parsedLink[1]).updatedAt());
    }
}
