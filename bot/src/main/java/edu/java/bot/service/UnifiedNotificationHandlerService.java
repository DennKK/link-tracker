package edu.java.bot.service;

import edu.java.bot.ListenerBot;
import edu.java.payload.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnifiedNotificationHandlerService implements NotificationHandlerService {
    private final ListenerBot listenerBot;

    @Override
    public void handleNotification(LinkUpdateRequest updateRequest) {
        listenerBot.updateRequest(updateRequest);
    }
}
