package edu.java.scrapper.configuration.kafka;

import edu.java.payload.dto.request.LinkUpdateRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {
    @Value("${app.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${app.kafka.consumer.group-id}")
    private String groupId;
    @Value("${app.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, LinkUpdateRequest> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(), valueDeserializer());
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        return props;
    }

    private JsonDeserializer<LinkUpdateRequest> valueDeserializer() {
        JsonDeserializer<LinkUpdateRequest> deserializer = new JsonDeserializer<>(LinkUpdateRequest.class);
        deserializer.addTrustedPackages("*");
        return deserializer;
    }
}
