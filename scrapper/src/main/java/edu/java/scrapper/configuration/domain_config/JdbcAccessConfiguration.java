package edu.java.scrapper.configuration.domain_config;

import edu.java.scrapper.domain.repository.jdbc.JdbcChatRepository;
import edu.java.scrapper.domain.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.jdbc.JdbcChatService;
import edu.java.scrapper.service.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(JdbcLinkRepository linkRepository, JdbcChatRepository chatRepository) {
        return new JdbcLinkService(linkRepository, chatRepository);
    }

    @Bean
    public ChatService chatService(JdbcChatRepository chatRepository) {
        return new JdbcChatService(chatRepository);
    }
}
