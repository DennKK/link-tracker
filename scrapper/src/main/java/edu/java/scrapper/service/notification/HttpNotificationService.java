package edu.java.scrapper.service.notification;

import edu.java.payload.dto.request.LinkUpdateRequest;
import edu.java.scrapper.client.tgbot.BotClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HttpNotificationService implements NotificationService {
    private final BotClient botClient;

    @Override
    public void sendNotification(LinkUpdateRequest update) {
        botClient.sendUpdateToBot(update);
    }
}
