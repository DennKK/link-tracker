package edu.java.scrapper.configuration;

import edu.java.scrapper.configuration.domain.AccessType;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull int updateFrequency,
    @NotNull
    AccessType accessType,
    @NotNull
    Scheduler scheduler,
    @NotNull
    RetryConfig retry
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record RetryConfig(
        boolean enable,
        @NotNull String policy,
        @NotNull
        List<Integer> statusCodes,
        Linear linear,
        Constant constant,
        Exponential exponential
    ) {
    }

    public record Linear(@NotNull int attempts, @NotNull Duration initialBackoff, @NotNull Duration maxBackoff) {
    }

    public record Constant(@NotNull int attempts, @NotNull Duration backoff) {
    }

    public record Exponential(@NotNull int attempts, @NotNull Duration initialBackoff, @NotNull Duration maxBackoff,
                              @NotNull double jitter) {
    }
}
