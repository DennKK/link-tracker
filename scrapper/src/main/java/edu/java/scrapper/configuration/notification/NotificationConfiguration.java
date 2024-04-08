package edu.java.scrapper.configuration.notification;

import edu.java.scrapper.client.tgbot.BotClient;
import edu.java.scrapper.service.notification.HttpNotificationSenderService;
import edu.java.scrapper.service.notification.NotificationSenderService;
import edu.java.scrapper.service.notification.QueueNotificationSenderService;
import edu.java.scrapper.service.queue.ScrapperQueueProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {
    @Value("${app.useQueue:true}")
    private boolean useQueue;

    @Bean
    public NotificationSenderService notificationService(ScrapperQueueProducer queueProducer, BotClient botClient) {
        if (useQueue) {
            return new QueueNotificationSenderService(queueProducer);
        } else {
            return new HttpNotificationSenderService(botClient);
        }
    }
}
