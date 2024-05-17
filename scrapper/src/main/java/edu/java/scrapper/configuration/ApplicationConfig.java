package edu.java.scrapper.configuration;

import edu.java.scrapper.configuration.domain.AccessType;
import edu.java.scrapper.configuration.kafka.property.KafkaPropertyConfig;
import edu.java.scrapper.configuration.retry.RetryConfig;
import edu.java.scrapper.configuration.scheduler.Scheduler;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull int updateFrequency,
    @NotNull
    AccessType accessType,
    @NotNull
    Scheduler scheduler,
    @NotNull
    boolean useQueue,
    @NotNull
    RetryConfig retry,
    @NotNull
    int rateLimit,
    @NotNull
    int timeDuration,
    @NotNull
    long nanoInSeconds,
    @NotNull
    KafkaPropertyConfig kafka
) {
}
