package edu.java.scrapper.configuration.retry.types;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;

public record Constant(@NotNull int attempts, @NotNull Duration backoff) {
}

