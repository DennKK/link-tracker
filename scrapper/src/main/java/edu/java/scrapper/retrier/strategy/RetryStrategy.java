package edu.java.scrapper.retrier.strategy;

import reactor.util.retry.Retry;

public interface RetryStrategy {
    Retry getRetryPolicy();
}
