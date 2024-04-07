package edu.java.bot.configuration.retry;

import edu.java.bot.configuration.retry.type.Constant;
import edu.java.bot.configuration.retry.type.Exponential;
import edu.java.bot.configuration.retry.type.Linear;
import jakarta.validation.constraints.NotNull;
import java.util.List;

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
