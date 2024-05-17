package edu.java.scrapper.service.notification;

import edu.java.payload.dto.request.LinkUpdateRequest;
import edu.java.scrapper.client.tgbot.BotClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpNotificationSenderService implements NotificationSenderService {
    private final BotClient botClient;

    @Override
    public void sendNotification(LinkUpdateRequest update) {
        log.info("Sending notification to the bot");
        botClient.sendUpdateToBot(update);
    }
}
