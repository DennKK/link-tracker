package edu.java.bot.configuration.retry;

import edu.java.bot.retrier.strategy.ConstantRetryStrategy;
import edu.java.bot.retrier.strategy.RetryStrategy;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app.retry", name = "policy", havingValue = "constant")
public class ConstantStrategyConfiguration {
    @Value("${app.retry.constant.attempts}")
    private int attempts;
    @Value("${app.retry.constant.backoff}")
    private Duration backoff;
    @Value("${app.retry.status-codes}")
    private List<Integer> retryStatusCodes;

    @Bean
    public RetryStrategy retryStrategy() {
        return new ConstantRetryStrategy(attempts, backoff, retryStatusCodes);
    }
}
