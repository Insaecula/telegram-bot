package pro.sky.telegrambot.NotificationTaskRepository;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    private final TelegramBot bot;

    public MessageSender(TelegramBot bot) {
        this.bot = bot;
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        bot.execute(message);
    }
}