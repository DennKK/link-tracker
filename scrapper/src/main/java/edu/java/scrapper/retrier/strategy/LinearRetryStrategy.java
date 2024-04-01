package edu.java.scrapper.retrier.strategy;

import java.time.Duration;
import org.springframework.stereotype.Component;
import reactor.util.retry.Retry;

@Component
public class LinearRetryStrategy implements RetryStrategy {
    @Override
    public Retry getRetryPolicy() {
        return Retry.backoff(5, Duration.ofSeconds(1))
            .maxBackoff(Duration.ofSeconds(10));
    }
}
