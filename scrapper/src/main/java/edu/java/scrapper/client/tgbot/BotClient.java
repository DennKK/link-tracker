package edu.java.scrapper.client.tgbot;

import edu.java.payload.dto.request.LinkUpdateRequest;
import edu.java.payload.dto.response.ApiErrorResponse;
import edu.java.scrapper.client.exception.BotClientException;
import edu.java.scrapper.domain.dto.LinkDto;
import edu.java.scrapper.retrier.strategy.RetryStrategy;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class BotClient {
    private final WebClient webClient;
    private final RetryStrategy retryStrategy;

    public BotClient(@NotNull String baseUrl, RetryStrategy retryStrategy) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.retryStrategy = retryStrategy;
    }

    public void sendUpdateToBot(LinkDto link, List<Long> chatIds) {
        log.info("Sending update to bot for Link ID: {}, URL: {}", link.getLinkId(), link.getUrl());
        LinkUpdateRequest request = new LinkUpdateRequest(link.getLinkId(), link.getUrl(), "\n"
            + "The link has a new activity!", chatIds);
        update(request);
    }

    public void update(LinkUpdateRequest linkUpdateRequest) {
        log.info("Preparing to post update: {}", linkUpdateRequest);
        webClient.post().uri("/updates")
            .body(BodyInserters.fromValue(linkUpdateRequest))
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> {
                    log.error("Error updating link: {}", error.exceptionMessage());
                    return Mono.error(new BotClientException(error.exceptionMessage()));
                }))
            .bodyToMono(Void.class)
            .retryWhen(retryStrategy.getRetryPolicy())
            .doOnError(e -> log.error("Error sending update to bot", e))
            .block();
    }
}
