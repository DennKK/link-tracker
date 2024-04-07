package edu.java.bot.configuration.retry.type;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;

public record Exponential(@NotNull int attempts, @NotNull Duration initialBackoff, @NotNull Duration maxBackoff,
                          @NotNull double jitter) {
}
