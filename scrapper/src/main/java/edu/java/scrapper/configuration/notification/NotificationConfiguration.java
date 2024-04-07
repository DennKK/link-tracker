package edu.java.scrapper.configuration.notification;

import edu.java.scrapper.client.tgbot.BotClient;
import edu.java.scrapper.service.notification.HttpNotificationService;
import edu.java.scrapper.service.notification.NotificationService;
import edu.java.scrapper.service.notification.QueueNotificationService;
import edu.java.scrapper.service.queue.ScrapperQueueProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {
    @Value("${app.useQueue:true}")
    private boolean useQueue;

    @Bean
    public NotificationService notificationService(ScrapperQueueProducer queueProducer, BotClient botClient) {
        if (useQueue) {
            return new QueueNotificationService(queueProducer);
        } else {
            return new HttpNotificationService(botClient);
        }
    }
}
