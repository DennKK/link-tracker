package edu.java.scrapper.controller.api;

import edu.java.payload.dto.request.AddLinkRequest;
import edu.java.payload.dto.request.RemoveLinkRequest;
import edu.java.payload.dto.response.ApiErrorResponse;
import edu.java.payload.dto.response.LinkResponse;
import edu.java.payload.dto.response.ListLinksResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Validated
public interface LinkApi {
    @Operation(summary = "Получить все отслеживаемые ссылки", responses = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылки успешно получены",
                     content = @Content(schema = @Schema(implementation = LinkResponse.class))),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @GetMapping(value = "/links", produces = {"application/json"})
    ListLinksResponse linksGet(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId);

    @Operation(summary = "Добавить отслеживание ссылки", responses = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылка успешно добавлена",
                     content = @Content(schema = @Schema(implementation = LinkResponse.class))),
        @ApiResponse(responseCode = "409",
                     description = "Ссылка уже добавлена",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/links", produces = {"application/json"}, consumes = {"application/json"})
    LinkResponse linksPost(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId, @RequestBody AddLinkRequest body);

    @Operation(summary = "Убрать отслеживание ссылки", responses = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылка успешно убрана",
                     content = @Content(schema = @Schema(implementation = LinkResponse.class))),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "404",
                     description = "Ссылка не найдена",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @DeleteMapping(value = "/links", produces = {"application/json"}, consumes = {"application/json"})
    LinkResponse linksDelete(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId, @RequestBody RemoveLinkRequest body);
}
