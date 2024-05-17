package edu.java.bot;

import edu.java.bot.configuration.ApplicationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = context.getBean(ApplicationConfig.class);
        log.info(config.toString());
        context.getBean(Bot.class).start();
    }
}
