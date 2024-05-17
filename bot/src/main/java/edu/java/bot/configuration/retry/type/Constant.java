package edu.java.bot.configuration.retry.type;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;

public record Constant(@NotNull int attempts, @NotNull Duration backoff) {
}
