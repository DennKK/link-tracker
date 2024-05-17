package edu.java.bot.controller;

import edu.java.bot.controller.api.BotApi;
import edu.java.bot.service.NotificationHandlerService;
import edu.java.payload.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BotController implements BotApi {
    private final NotificationHandlerService notificationHandlerService;

    @Override
    public void updatesPost(LinkUpdateRequest request) {
        log.info("Received new update request: {}", request);
        try {
            notificationHandlerService.handleNotification(request);
            log.info("Successfully handled update request for Link ID: {}", request.id());
        } catch (Exception e) {
            log.error("Error handling update request for Link ID: {}", request.id(), e);
        }
    }
}
