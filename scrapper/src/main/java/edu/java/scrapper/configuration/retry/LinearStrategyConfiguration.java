package edu.java.scrapper.configuration.retry;

import edu.java.scrapper.retrier.strategy.LinearRetryStrategy;
import edu.java.scrapper.retrier.strategy.RetryStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app.retry", name = "policy", havingValue = "linear")
public class LinearStrategyConfiguration {
    @Bean
    public RetryStrategy retryStrategy() {
        return new LinearRetryStrategy();
    }
}
