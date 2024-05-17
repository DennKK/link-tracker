package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.exception.ScrapperClientException;
import edu.java.payload.dto.response.LinkResponse;
import edu.java.payload.dto.response.ListLinksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "shows a list of tracked links";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        ListLinksResponse response;

        try {
            response = scrapperClient.getAllLinks(chatId);
        } catch (ScrapperClientException ex) {
            return new SendMessage(chatId, ex.getMessage());
        } catch (RuntimeException ex) {
            return new SendMessage(chatId, "Something went wrong!");
        }

        if (response.size() == 0) {
            return new SendMessage(chatId, "No tracked links yet!");
        }

        StringBuilder sendMessageText = new StringBuilder("Tracked links:");
        for (LinkResponse linkResponse : response.linkResponseList()) {
            sendMessageText.append("\n").append(linkResponse.url().toString());
        }

        return new SendMessage(chatId, sendMessageText.toString());
    }
}
