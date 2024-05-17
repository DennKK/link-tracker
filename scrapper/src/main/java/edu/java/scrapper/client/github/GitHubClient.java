package edu.java.scrapper.client.github;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GitHubClient implements GitHubClientInterface {
    private final WebClient webClient;

    public GitHubClient(@Qualifier("gitHubWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public GitHubResponse getGitHubResponse(String username, String repo) {
        return webClient
            .get()
            .uri("/repos/{username}/{repo}", username, repo)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .block();
    }
}
