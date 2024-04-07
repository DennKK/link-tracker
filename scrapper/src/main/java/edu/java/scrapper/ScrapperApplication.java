package edu.java.scrapper;

import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.configuration.kafka.KafkaPropertyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, KafkaPropertyConfig.class})
public class ScrapperApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = context.getBean(ApplicationConfig.class);
        log.info(config.toString());
    }
}
