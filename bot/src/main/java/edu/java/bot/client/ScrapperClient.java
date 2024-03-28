package edu.java.bot.client;

import edu.java.bot.client.exception.ScrapperClientException;
import edu.java.payload.dto.request.AddLinkRequest;
import edu.java.payload.dto.request.RemoveLinkRequest;
import edu.java.payload.dto.response.ApiErrorResponse;
import edu.java.payload.dto.response.LinkResponse;
import edu.java.payload.dto.response.ListLinksResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperClient {
    private final WebClient webClient;

    private static final String LINKS_PATH = "/links";
    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";
    private static final String TG_CHAT_PATH = "/tg-chat/{id}";

    public ScrapperClient(@NotNull String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public void addChat(Long id) {
        webClient.post().uri(TG_CHAT_PATH, id)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new ScrapperClientException(error.exceptionMessage()))))
            .bodyToMono(Void.class)
            .block();
    }

    public void deleteChat(Long id) {
        webClient.delete().uri(TG_CHAT_PATH, id)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new ScrapperClientException(error.exceptionMessage()))))
            .bodyToMono(Void.class).block();
    }

    public ListLinksResponse getAllLinks(long id) {
        return webClient.get()
            .uri(LINKS_PATH)
            .header(TG_CHAT_ID_HEADER, String.valueOf(id))
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new ScrapperClientException(error.exceptionMessage()))))
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    public LinkResponse removeLink(Long id, String removeLink) {
        return webClient.method(HttpMethod.DELETE)
            .uri(LINKS_PATH, id)
            .header(TG_CHAT_ID_HEADER, String.valueOf(id))
            .body(BodyInserters.fromValue(new RemoveLinkRequest(removeLink)))
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new ScrapperClientException(error.exceptionMessage()))))
            .bodyToMono(LinkResponse.class)
            .block();
    }

    public LinkResponse addLink(long id, String url) {
        return webClient.post()
            .uri(LINKS_PATH, id)
            .header(TG_CHAT_ID_HEADER, String.valueOf(id))
            .body(BodyInserters.fromValue(new AddLinkRequest(url)))
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new ScrapperClientException(error.exceptionMessage()))))
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
