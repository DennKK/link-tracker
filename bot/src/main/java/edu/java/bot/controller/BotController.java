package edu.java.bot.controller;

import edu.java.bot.api.BotApi;
import edu.java.bot.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BotController implements BotApi {
    @Override
    public void updatesPost(LinkUpdateRequest request) {
        log.info("new update request " + request.description());
    }
}