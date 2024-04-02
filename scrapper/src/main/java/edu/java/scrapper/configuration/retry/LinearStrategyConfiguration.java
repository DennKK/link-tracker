package edu.java.scrapper.configuration.retry;

import edu.java.retry.strategy.LinearRetryStrategy;
import edu.java.retry.strategy.RetryStrategy;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app.retry", name = "policy", havingValue = "linear")
public class LinearStrategyConfiguration {
    @Value("${app.retry.linear.attempts}")
    private int attempts;
    @Value("${app.retry.linear.initial-backoff}")
    private Duration initialBackoff;
    @Value("${app.retry.linear.max-backoff}")
    private Duration maxBackoff;
    @Value("${app.retry.status-codes}")
    private List<Integer> retryStatusCodes;

    @Bean
    public RetryStrategy retryStrategy() {
        return new LinearRetryStrategy(attempts, initialBackoff, maxBackoff, retryStatusCodes);
    }
}
