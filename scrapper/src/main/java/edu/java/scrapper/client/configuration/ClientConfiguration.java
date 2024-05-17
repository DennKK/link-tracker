package edu.java.scrapper.client.configuration;

import edu.java.scrapper.client.tgbot.BotClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

@Validated
@Configuration
public class ClientConfiguration {
    @Value("${clients.base-url.github:https://api.github.com}")
    private String gitHubBaseUrl;
    @Value("${clients.base-url.stackoverflow:https://api.stackexchange.com/2.3}")
    private String stackoverflowBaseUrl;
    @Value("${clients.base-url.bot:http://localhost:8090}")
    private String botBaseUrl;

    @Bean("gitHubWebClient")
    public WebClient getGitHubClient() {
        return WebClient
            .builder()
            .baseUrl(gitHubBaseUrl)
            .build();
    }

    @Bean("stackoverflowClient")
    public WebClient getStackoverflowClient() {
        return WebClient
            .builder()
            .baseUrl(stackoverflowBaseUrl)
            .build();
    }

    @Bean
    public BotClient getBotClient() {
        return new BotClient(botBaseUrl);
    }
}
