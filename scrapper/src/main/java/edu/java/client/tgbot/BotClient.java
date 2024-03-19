package edu.java.client.tgbot;

import edu.java.client.exception.BotClientException;
import edu.java.payload.dto.request.LinkUpdateRequest;
import edu.java.payload.dto.response.ApiErrorResponse;
import edu.java.scrapper.domain.dto.ChatDto;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.service.jdbc.JdbcLinkService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BotClient {
    private final WebClient webClient;
    private final JdbcLinkService jdbcLinkService;

    public BotClient(@Qualifier("telegramBotClient") WebClient webClient, JdbcLinkService jdbcLinkService) {
        this.webClient = webClient;
        this.jdbcLinkService = jdbcLinkService;
    }

    public void sendUpdateToBot(LinkDto link) {
        List<Long> chats =
            jdbcLinkService.getChatsForLink(link).stream().map(ChatDto::getChatId).toList();
        LinkUpdateRequest request = new LinkUpdateRequest(link.getLinkId(), link.getUrl(), "New update", chats);
        update(request);
    }

    public void update(LinkUpdateRequest linkUpdateRequest) {
        webClient.post().uri("/updates")
            .body(BodyInserters.fromValue(linkUpdateRequest))
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new BotClientException(error.exceptionMessage()))))
            .bodyToMono(Void.class)
            .block();
    }
}
