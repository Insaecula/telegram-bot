package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.enity.NotificationTask;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        Pattern pattern = Pattern.compile("^(\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2})\\s+(.+)$");

        for (Update update : updates) {
            if (update.message() == null || update.message().text() == null) continue;

            String messageText = update.message().text();
            Long chatId = update.message().chat().id();

            if ("/start".equals(messageText)) {
                bot.execute(new SendMessage(chatId, "Привет! Я бот-напоминалка. Отправь мне сообщение в формате:\n\n📅 `01.01.2025 18:00 Сделать домашку`"));
                continue;
            }

            Matcher matcher = pattern.matcher(messageText);
            if (matcher.matches()) {
                try {
                    String dateTimeStr = matcher.group(1);
                    String taskText = matcher.group(2);
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                    NotificationTask task = new NotificationTask();
                    task.setChatId(chatId);
                    task.setMessage(taskText);
                    task.setNotificationTime(dateTime);

                    repository.save(task);

                    bot.execute(new SendMessage(chatId, "✅ Напоминание сохранено!"));
                } catch (Exception e) {
                    bot.execute(new SendMessage(chatId, "❌ Ошибка при разборе даты. Используй формат: 01.01.2025 18:00 Текст"));
                }
            } else {
                bot.execute(new SendMessage(chatId, "❓ Не распознано. Используй формат:\n01.01.2025 18:00 Сделать что-то"));
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
