package edu.java.bot.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageCounter {
    private final Counter counter;

    @Autowired
    public MessageCounter(MeterRegistry registry) {
        counter = Counter.builder("message_counter")
            .description("Number of messages processed")
            .register(registry);
    }

    public void increment() {
        counter.increment();
    }
}
