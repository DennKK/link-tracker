package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.Command;
import edu.java.bot.message.MessageProcessor;
import edu.java.bot.metric.MessageCounter;
import edu.java.payload.dto.request.LinkUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListenerBot implements Bot {
    private final TelegramBot listenerBot;
    private final MessageProcessor messageProcessor;
    private final MessageCounter messageCounter;

    public void createMenu() {
        List<? extends Command> commands = messageProcessor.commands();
        BotCommand[] menu = new BotCommand[commands.size()];
        for (int i = 0; i < commands.size(); i++) {
            menu[i] = commands.get(i).toApiCommand();
        }
        listenerBot.execute(new SetMyCommands(menu));
    }

    @Override
    public void start() {
        createMenu();
        listenerBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                messageCounter.increment();
                listenerBot.execute(process(update));
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    @Override
    public SendMessage process(Update update) {
        return messageProcessor.process(update);
    }

    public void updateRequest(LinkUpdateRequest updateRequest) {
        List<Long> chats = updateRequest.tgChatIds();
        String link = updateRequest.url();
        String description = updateRequest.description();

        for (Long chat : chats) {
            String message = "New update for " + link + ":\n" + description;
            SendMessage sendMessage = new SendMessage(chat, message);
            listenerBot.execute(sendMessage);
        }
    }
}
