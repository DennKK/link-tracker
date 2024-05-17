package edu.java.bot.configuration.client;

import edu.java.bot.client.ScrapperClient;
import edu.java.retry.strategy.RetryStrategy;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
public class ClientConfiguration {
    @NotNull
    @Value("${clients.base-url.scrapper:http://localhost:8080}")
    String baseUrlScrapper;

    @Bean
    public ScrapperClient getScrapperClient(RetryStrategy retryStrategy) {
        return new ScrapperClient(baseUrlScrapper, retryStrategy);
    }
}
