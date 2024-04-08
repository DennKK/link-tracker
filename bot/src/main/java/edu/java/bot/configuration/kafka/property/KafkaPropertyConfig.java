package edu.java.bot.configuration.kafka.property;

import jakarta.validation.constraints.NotNull;

public record KafkaPropertyConfig(
    @NotNull KafkaConsumer consumer,
    @NotNull Topic topic,
    @NotNull String bootstrapServers
) {
}
