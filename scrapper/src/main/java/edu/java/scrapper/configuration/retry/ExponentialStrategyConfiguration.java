package edu.java.scrapper.configuration.retry;

import edu.java.retry.strategy.ExponentialRetryStrategy;
import edu.java.retry.strategy.RetryStrategy;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app.retry", name = "policy", havingValue = "exponential")
public class ExponentialStrategyConfiguration {

    @Value("${app.retry.exponential.attempts}")
    private int attempts;

    @Value("${app.retry.exponential.initial-backoff}")
    private Duration initialBackoff;

    @Value("${app.retry.exponential.max-backoff}")
    private Duration maxBackoff;

    @Value("${app.retry.exponential.jitter}")
    private double jitter;
    @Value("${app.retry.status-codes}")
    private List<Integer> retryStatusCodes;

    @Bean
    public RetryStrategy exponentialRetryStrategy() {
        return new ExponentialRetryStrategy(attempts, initialBackoff, maxBackoff, jitter, retryStatusCodes);
    }
}
