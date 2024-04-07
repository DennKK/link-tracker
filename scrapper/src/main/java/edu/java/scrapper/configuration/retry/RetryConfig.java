package edu.java.scrapper.configuration.retry;

import edu.java.scrapper.configuration.retry.types.Constant;
import edu.java.scrapper.configuration.retry.types.Exponential;
import edu.java.scrapper.configuration.retry.types.Linear;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RetryConfig(
    boolean enable,
    @NotNull String policy,
    @NotNull List<Integer> statusCodes,
    Linear linear,
    Constant constant,
    Exponential exponential
) {
}
