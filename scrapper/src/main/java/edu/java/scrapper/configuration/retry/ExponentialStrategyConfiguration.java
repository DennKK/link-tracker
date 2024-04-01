package edu.java.scrapper.configuration.retry;

import edu.java.scrapper.retrier.strategy.ExponentialRetryStrategy;
import edu.java.scrapper.retrier.strategy.RetryStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app.retry", name = "policy", havingValue = "exponential")
public class ExponentialStrategyConfiguration {
    @Bean
    public RetryStrategy retryStrategy() {
        return new ExponentialRetryStrategy();
    }
}
