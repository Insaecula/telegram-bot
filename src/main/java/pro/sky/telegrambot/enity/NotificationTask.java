package pro.sky.telegrambot.enity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task")
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;
    private String message;
    private LocalDateTime notificationTime;
    private  Long chat;
    private LocalDateTime clock;

    public NotificationTask(Long id, Long chat, String message, LocalDateTime clock){
        this.id = id;
        this.chat = chat;
        this.clock = clock;
        this.message = message;
    }
    public NotificationTask(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChat() {
        return chat;
    }

    public void setChat(Long chat) {
        this.chat = chat;
    }

    public LocalDateTime getClock() {
        return clock;
    }

    public void setClock(LocalDateTime clock) {
        this.clock = clock;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}