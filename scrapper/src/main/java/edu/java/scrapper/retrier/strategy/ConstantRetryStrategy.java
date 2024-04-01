package edu.java.scrapper.retrier.strategy;

import reactor.util.retry.Retry;
import java.time.Duration;

public class ConstantRetryStrategy implements RetryStrategy {
    @Override
    public Retry getRetryPolicy() {
        return Retry.fixedDelay(3, Duration.ofSeconds(5));
    }
}
