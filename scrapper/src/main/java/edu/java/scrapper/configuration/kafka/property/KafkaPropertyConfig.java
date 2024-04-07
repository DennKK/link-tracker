package edu.java.scrapper.configuration.kafka.property;

import jakarta.validation.constraints.NotNull;

public record KafkaPropertyConfig(
    @NotNull KafkaProducer producer,
    @NotNull KafkaConsumer consumer,
    @NotNull Topic topic,
    @NotNull String bootstrapServers
) {
}
