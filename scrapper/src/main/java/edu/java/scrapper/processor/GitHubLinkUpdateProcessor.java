package edu.java.scrapper.processor;

import edu.java.payload.dto.request.LinkUpdateRequest;
import edu.java.scrapper.client.github.GitHubClient;
import edu.java.scrapper.client.github.GitHubResponse;
import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.notification.NotificationSenderService;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubLinkUpdateProcessor implements LinkUpdateProcessor {
    private final GitHubClient gitHubClient;
    private final NotificationSenderService notificationService;
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
            log.error("Error updating link {}", link.getUrl(), e);
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
        linkService.refreshLinkActivity(link);
    }

    private void notifySubscribers(LinkDto link) {
        Collection<ChatDto> chats = linkService.getChatsForLink(link);
        List<Long> tgChatIds = chats.stream().map(ChatDto::getTgChatId).collect(Collectors.toList());
        LinkUpdateRequest updateRequest =
            new LinkUpdateRequest(link.getLinkId(), link.getUrl(), "The link has a new activity!", tgChatIds);

        notificationService.sendNotification(updateRequest);
    }

    private String[] parseGithubLink(String url) {
        String[] parts = url.substring("https://github.com/".length()).split("/");
        return new String[] {parts[0], parts[1]};
    }
}
