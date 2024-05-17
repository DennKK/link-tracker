package edu.java.scrapper.configuration.retry.types;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;

public record Exponential(@NotNull int attempts, @NotNull Duration initialBackoff, @NotNull Duration maxBackoff,
                          @NotNull double jitter) {
}
