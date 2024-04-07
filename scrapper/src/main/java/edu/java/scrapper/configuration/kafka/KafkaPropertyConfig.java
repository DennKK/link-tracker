package edu.java.scrapper.configuration.kafka;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.kafka", ignoreUnknownFields = false)
public record KafkaPropertyConfig(
    @NotNull
    KafkaConfig kafka
) {
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
