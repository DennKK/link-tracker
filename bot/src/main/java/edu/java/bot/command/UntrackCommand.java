package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.exception.ScrapperClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private final static String REPLY_TEXT = "Send the link that you want to untrack!";
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public boolean supports(Update update) {
        return update.message().text().trim().equals(command()) || isReply(update);
    }

    @Override
    public String description() {
        return "stops tracking link";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();

        if (isReply(update)) {
            return handleReply(update);
        }
        return new SendMessage(chatId, REPLY_TEXT).replyMarkup(new ForceReply());

    }

    private SendMessage handleReply(Update update) {
        long chatId = update.message().chat().id();
        String url = update.message().text().trim();
        String replyMessage = "Now we are NOT tracking your link!";

        try {
            scrapperClient.removeLink(chatId, url);
        } catch (ScrapperClientException exception) {
            replyMessage = exception.getMessage();
        } catch (RuntimeException ex) {
            replyMessage = "Ooops! Something went wrong!";
        }

        return new SendMessage(chatId, replyMessage);
    }

    private boolean isReply(Update update) {
        return update.message().replyToMessage() != null && update.message().replyToMessage().text().equals(REPLY_TEXT);
    }
}
