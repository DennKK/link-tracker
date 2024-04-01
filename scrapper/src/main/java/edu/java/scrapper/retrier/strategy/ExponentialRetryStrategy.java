package edu.java.scrapper.retrier.strategy;

import reactor.util.retry.Retry;
import java.time.Duration;

public class ExponentialRetryStrategy implements RetryStrategy {
    @Override
    public Retry getRetryPolicy() {
        return Retry.backoff(3, Duration.ofSeconds(1))
            .maxBackoff(Duration.ofSeconds(20))
            .jitter(0.5);
    }
}
