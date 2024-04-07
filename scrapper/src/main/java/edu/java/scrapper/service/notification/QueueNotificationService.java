package edu.java.scrapper.service.notification;

import edu.java.payload.dto.request.LinkUpdateRequest;
import edu.java.scrapper.service.queue.ScrapperQueueProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueNotificationService implements NotificationService {
    private final ScrapperQueueProducer queueProducer;

    @Override
    public void sendNotification(LinkUpdateRequest updateRequest) {
        queueProducer.send(updateRequest);
    }
}
