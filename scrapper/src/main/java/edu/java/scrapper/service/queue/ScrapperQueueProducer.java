package edu.java.scrapper.service.queue;

import edu.java.payload.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {
    private final KafkaTemplate<String, LinkUpdateRequest> template;
    private final NewTopic topic;

    public void send(LinkUpdateRequest updateRequest) {
        log.info("Preparing to send update request to topic {}: {}", topic.name(), updateRequest);

        try {
            this.template.send(topic.name(), updateRequest);
            log.info("Update request sent to topic {}: {}", topic.name(), updateRequest);
        } catch (Exception e) {
            log.error(
                "Failed to send update request to topic {}: {}, due to error: {}",
                topic.name(),
                updateRequest,
                e.getMessage()
            );
        }
    }
}
