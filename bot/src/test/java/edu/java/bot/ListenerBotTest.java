package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.utility.BotUtils;
import edu.java.bot.command.Command;
import edu.java.bot.command.HelpCommand;
import edu.java.bot.command.StartCommand;
import edu.java.bot.command.UntrackCommand;
import edu.java.bot.message.MessageProcessor;
import edu.java.bot.metric.MessageCounter;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ListenerBotTest {
    @Test
    public void unknownCommandTest() {
        MessageProcessor messageProcessor = new MessageProcessor(List.of(new StartCommand(null)));
        ListenerBot listenerBot = new ListenerBot(null, messageProcessor, mock(MessageCounter.class));
        String testCommand = "/someCommand";

        Update update = BotUtils.parseUpdate("{\"message\":{\"text\":\"" + testCommand + "\",\"chat\":{\"id\":0}}}");

        SendMessage message = listenerBot.process(update);

        String text = message.getParameters().get("text").toString();
        assertEquals("There is no such command as " + testCommand, text);
    }

    @Test
    public void helpCommandTest() {
        List<Command> commands =
            List.of(
                new StartCommand(null),
                new UntrackCommand(null),
                new HelpCommand(List.of(
                    new StartCommand(null),
                    new UntrackCommand(null)
                ))
            );

        MessageProcessor mp = new MessageProcessor(commands);
        ListenerBot bot = new ListenerBot(null, mp, mock(MessageCounter.class));

        String testCommand = "/help";

        String expectedResult = """
            /start : starts interacting with the bot
            /untrack : stops tracking link
            /help : shows available commands""";

        Update update = BotUtils.parseUpdate("{\"message\":{\"text\":\"" + testCommand + "\",\"chat\":{\"id\":0}}}");

        SendMessage message = bot.process(update);

        String text = message.getParameters().get("text").toString();
        assertEquals(expectedResult, text);
    }
}
