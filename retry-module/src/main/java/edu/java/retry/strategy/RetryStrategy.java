package edu.java.retry.strategy;

import reactor.util.retry.Retry;

public interface RetryStrategy {
    Retry getRetryPolicy();
}
