package edu.java.scrapper.configuration.kafka;

import edu.java.payload.dto.request.LinkUpdateRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfiguration {
    @Value("${app.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${app.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${app.kafka.producer.value-serializer}")
    private String valueSerializer;
    @Value("${app.kafka.producer.acks}")
    private String acks;
    @Value("${app.kafka.producer.retries}")
    private Integer retries;
    @Value("${app.kafka.producer.max-in-flight-requests-per-connection}")
    private Integer maxInFlightRequestsPerConnection;
    @Value("${app.kafka.producer.enable-idempotence}")
    private Boolean enableIdempotence;
    @Value("${app.kafka.producer.linger-ms}")
    private Integer lingerMs;

    @Bean
    public ProducerFactory<String, LinkUpdateRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(senderProps());
    }

    private Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequestsPerConnection);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        return props;
    }

    @Bean
    public KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate(
        ProducerFactory<String, LinkUpdateRequest> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
