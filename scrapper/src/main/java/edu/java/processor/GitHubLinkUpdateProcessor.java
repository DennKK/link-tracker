package edu.java.processor;

import edu.java.client.github.GitHubClient;
import edu.java.client.github.GitHubResponse;
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
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GitHubLinkUpdateProcessor implements LinkUpdateProcessor {
    private final GitHubClient gitHubClient;
    private final BotClient botClient;
    private final LinkService linkService;

    @Override
    public boolean supports(String url) {
        // String must be https://github.com/<username>/<repository>/ or https://github.com/<username>/<repository>
        final String GITHUB_URL_PATTERN = "https://github.com/([^/]+)/([^/]+)(/)?$";

        if (url == null) {
            return false;
        }

        return url.matches(GITHUB_URL_PATTERN);
    }

    @Override
    public void processLinkUpdate(LinkDto link) {
        try {
            GitHubResponse response = fetchGitHubResponse(link.getUrl());
            OffsetDateTime lastActivityTime = OffsetDateTime.parse(response.updatedAt());

            if (link.getUpdatedAt().isBefore(lastActivityTime)) {
                updateLinkDetails(link, lastActivityTime);
                notifySubscribers(link);
                log.info("Link updated successfully: {}", link.getUrl());
            } else {
                link.setCheckedAt(OffsetDateTime.now());
                log.info("No activity for link: {}", link.getUrl());
            }
        } catch (Exception e) {
            log.error("Error updating link {}: {}", link.getUrl(), e.getMessage());
        }
    }

    private GitHubResponse fetchGitHubResponse(String url) {
        String[] parsedLink = parseGithubLink(url);
        return gitHubClient.getGitHubResponse(parsedLink[0], parsedLink[1]);
    }

    private void updateLinkDetails(LinkDto link, OffsetDateTime lastActivityTime) {
        link.setCheckedAt(OffsetDateTime.now());
        link.setUpdatedAt(lastActivityTime);
        linkService.updateLastCheckTime(link);
        linkService.updateLastCheckTime(link);
    }

    private void notifySubscribers(LinkDto link) {
        Collection<ChatDto> chats = linkService.getChatsForLink(link);
        List<Long> tgChatIds = chats.stream().map(ChatDto::getTgChatId).collect(Collectors.toList());
        botClient.sendUpdateToBot(link, tgChatIds);
    }

    private String[] parseGithubLink(String url) {
        String[] parts = url.substring("https://github.com/".length()).split("/");
        return new String[] {parts[0], parts[1]};
    }
}
