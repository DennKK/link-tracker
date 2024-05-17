package edu.java.scrapper.configuration.scheduler;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;

public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
}
