package edu.java.retry.strategy;

import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

@Slf4j
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
            .doBeforeRetry(retrySignal -> {
                WebClientResponseException exception = (WebClientResponseException) retrySignal.failure();
                log.warn(
                    "Retry attempt {} due to response with code {}",
                    retrySignal.totalRetries(),
                    exception.getStatusCode()
                );
            });
    }

    private boolean shouldRetryOnStatusCode(Throwable throwable) {
        return throwable instanceof WebClientResponseException
            && retryStatusCodes.contains(((WebClientResponseException) throwable).getStatusCode().value());
    }
}
