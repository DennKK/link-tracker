package edu.java.bot.configuration;

import edu.java.bot.client.ScrapperClient;
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
    public ScrapperClient getScrapperClient() {
        return new ScrapperClient(baseUrlScrapper);
    }
}
