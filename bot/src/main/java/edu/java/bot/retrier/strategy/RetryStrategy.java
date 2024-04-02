package edu.java.bot.retrier.strategy;

import reactor.util.retry.Retry;

public interface RetryStrategy {
    Retry getRetryPolicy();
}
