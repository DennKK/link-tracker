package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.exception.ScrapperClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "starts interacting with the bot";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();

        String replyMessage =
            "Hi! \nThis bot allows you to track changes on Github and Stackoverflow! \nUse /help for more information";

        try {
            scrapperClient.addChat(chatId);
        } catch (ScrapperClientException exception) {
            replyMessage = exception.getMessage();
        } catch (RuntimeException exception) {
            replyMessage = "Ooops! Something went wrong!";
        }

        return new SendMessage(
            chatId, replyMessage
        );
    }
}
