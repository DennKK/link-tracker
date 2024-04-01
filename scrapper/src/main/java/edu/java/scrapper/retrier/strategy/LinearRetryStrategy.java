package edu.java.scrapper.retrier.strategy;

import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

@RequiredArgsConstructor
public class LinearRetryStrategy implements RetryStrategy {
    private final int attempts;
    private final Duration initialBackoff;
    private final Duration maxBackoff;
    private final List<Integer> retryStatusCodes;

    @Override
    public Retry getRetryPolicy() {
        return Retry.backoff(attempts, initialBackoff)
            .maxBackoff(maxBackoff)
            .filter(this::shouldRetryOnStatusCode)
            .doBeforeRetry(retrySignal -> System.out.println("Retry due to response with code " +
                ((WebClientResponseException) retrySignal.failure()).getStatusCode()));
    }

    private boolean shouldRetryOnStatusCode(Throwable throwable) {
        return throwable instanceof WebClientResponseException &&
            retryStatusCodes.contains(((WebClientResponseException) throwable).getStatusCode().value());
    }
}
