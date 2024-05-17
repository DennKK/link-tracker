package edu.java.scrapper.service.notification;

import edu.java.payload.dto.request.LinkUpdateRequest;
import edu.java.scrapper.service.queue.ScrapperQueueProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueNotificationSenderService implements NotificationSenderService {
    private final ScrapperQueueProducer queueProducer;

    @Override
    public void sendNotification(LinkUpdateRequest updateRequest) {
        log.info("Sending message to queue");
        queueProducer.send(updateRequest);
    }
}
