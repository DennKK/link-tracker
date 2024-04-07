package edu.java.scrapper.configuration.kafka.property;

import jakarta.validation.constraints.NotNull;

public record KafkaConsumer(
    @NotNull String groupId,
    @NotNull String keyDeserializer,
    @NotNull String valueDeserializer,
    @NotNull String autoOffsetReset
) {
}
