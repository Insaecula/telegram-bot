package pro.sky.telegrambot.NotificationTaskRepository;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enity.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructorD
public class NotificationScheduler {

    private final NotificationTaskRepository repository;
    private final TelegramBot telegramBot;

    public NotificationScheduler(NotificationTaskRepository repository, TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendNotifications() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        List<NotificationTask> tasks = repository.findByNotificationTime(now);

        for (NotificationTask task : tasks) {
            telegramBot.execute(new SendMessage(task.getChatId(), "🔔 Напоминание: " + task.getMessage()));
        }
    }
}
