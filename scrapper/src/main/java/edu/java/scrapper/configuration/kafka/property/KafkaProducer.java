package edu.java.scrapper.configuration.kafka.property;

import jakarta.validation.constraints.NotNull;

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
