package edu.java.bot.service;

import edu.java.payload.dto.request.LinkUpdateRequest;

public interface NotificationHandlerService {
    void handleNotification(LinkUpdateRequest updateRequest);
}
