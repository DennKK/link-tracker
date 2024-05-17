package edu.java.scrapper.service.notification;

import edu.java.payload.dto.request.LinkUpdateRequest;

public interface NotificationSenderService {
    void sendNotification(LinkUpdateRequest update);
}
