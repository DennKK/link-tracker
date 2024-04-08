package edu.java.bot.kafka;

import edu.java.bot.service.NotificationHandlerService;
import edu.java.payload.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdateKafkaListener {
    private final NotificationHandlerService notificationHandlerService;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    @KafkaListener(
        groupId = "${app.kafka.consumer.group-id}",
        topics = "${app.kafka.topic.name}",
        autoStartup = "true",
        containerFactory = "kafkaListenerContainerFactory")
    public void listen(LinkUpdateRequest updateRequest) {
        try {
            log.info("Received Kafka message on topic with Link ID - {}, URL - {}",
                updateRequest.id(), updateRequest.url()
            );

            notificationHandlerService.handleNotification(updateRequest);
            log.info("Successfully handled Kafka message for Link ID: {}", updateRequest.id());
        } catch (Exception e) {
            log.error("Error processing Kafka message, sending to DLQ: {}", e.getMessage());
            kafkaTemplate.send("${app.kafka.topic.dlq}", updateRequest);
        }
    }
}
