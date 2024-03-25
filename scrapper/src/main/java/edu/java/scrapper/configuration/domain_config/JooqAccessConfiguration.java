package edu.java.scrapper.configuration.domain_config;

import edu.java.scrapper.domain.repository.jooq.JooqChatRepository;
import edu.java.scrapper.domain.repository.jooq.JooqLinkRepository;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.service.jooq.JooqChatService;
import edu.java.scrapper.service.jooq.JooqLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public LinkService linkService(JooqLinkRepository linkRepository) {
        return new JooqLinkService(linkRepository);
    }

    @Bean
    public ChatService chatService(JooqChatRepository chatRepository) {
        return new JooqChatService(chatRepository);
    }
}
