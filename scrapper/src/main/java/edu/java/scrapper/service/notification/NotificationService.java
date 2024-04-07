package edu.java.scrapper.service.notification;

import edu.java.payload.dto.request.LinkUpdateRequest;

public interface NotificationService {
    void sendNotification(LinkUpdateRequest update);
}
