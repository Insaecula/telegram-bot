package pro.sky.telegrambot.NotificationTaskRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.enity.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
    List<NotificationTask> findByNotificationTime(LocalDateTime time);
}