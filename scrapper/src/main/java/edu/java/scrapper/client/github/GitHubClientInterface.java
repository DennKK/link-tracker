package edu.java.scrapper.client.github;

public interface GitHubClientInterface {
    GitHubResponse getGitHubResponse(String username, String repo);
}
