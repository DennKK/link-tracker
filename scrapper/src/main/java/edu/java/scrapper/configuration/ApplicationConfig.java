package edu.java.scrapper.configuration;

import edu.java.scrapper.configuration.domain.AccessType;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull int updateFrequency,
    @NotNull
    AccessType accessType,
    @NotNull
    boolean useQueue,
    @NotNull
    Scheduler scheduler,
    @NotNull
    RetryConfig retry,
    @NotNull
    int rateLimit,
    @NotNull
    int timeDuration,
    @NotNull
    long nanoInSeconds,
    @NotNull
    KafkaConfig kafka
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record RetryConfig(
        boolean enable,
        @NotNull String policy,
        @NotNull
        List<Integer> statusCodes,
        Linear linear,
        Constant constant,
        Exponential exponential
    ) {
    }

    public record Linear(@NotNull int attempts, @NotNull Duration initialBackoff, @NotNull Duration maxBackoff) {
    }

    public record Constant(@NotNull int attempts, @NotNull Duration backoff) {
    }

    public record Exponential(@NotNull int attempts, @NotNull Duration initialBackoff, @NotNull Duration maxBackoff,
                              @NotNull double jitter) {
    }

    public record KafkaConfig(
        @NotNull KafkaProducer producer,
        @NotNull KafkaConsumer consumer,
        @NotNull Topic topic,
        @NotNull String bootstrapServers
    ) {
    }

    public record KafkaProducer(
        @NotNull String keySerializer,
        @NotNull String valueSerializer,
        @NotNull String acks,
        @NotNull int lingerMs,
        @NotNull int retries,
        @NotNull int maxInFlightRequestsPerConnection,
        @NotNull boolean enableIdempotence
    ) {
    }

    public record KafkaConsumer(
        @NotNull String groupId,
        @NotNull String keyDeserializer,
        @NotNull String valueDeserializer,
        @NotNull String autoOffsetReset
    ) {
    }

    public record Topic(
        @NotNull String name,
        @NotNull Integer partitions,
        @NotNull Integer replicas
    ) {
    }
}
