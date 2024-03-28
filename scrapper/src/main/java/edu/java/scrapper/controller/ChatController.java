package edu.java.scrapper.controller;

import edu.java.scrapper.controller.api.ChatApi;
import edu.java.scrapper.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController implements ChatApi {
    private final ChatService service;

    @Override
    public void tgChatIdDelete(Long id) {
        log.info("Delete tg chat {}", id);
        service.unregister(id);
    }

    @Override
    public void tgChatIdPost(Long id) {
        log.info("Add tg chat {}", id);
        service.register(id);
    }
}
