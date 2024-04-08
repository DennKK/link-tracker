package edu.java.bot.configuration.kafka.property;

import jakarta.validation.constraints.NotNull;

public record Topic(
    @NotNull String name,
    @NotNull Integer partitions,
    @NotNull Integer replicas,
    @NotNull String dlq
) {
}
