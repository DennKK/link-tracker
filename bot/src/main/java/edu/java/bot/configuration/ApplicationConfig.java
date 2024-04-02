package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotNull
    RetryConfig retry

) {
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
