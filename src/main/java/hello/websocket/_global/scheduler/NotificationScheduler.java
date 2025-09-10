package hello.websocket._global.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    final SimpMessagingTemplate messagingTemplate;

    @Scheduled(cron = "0 * * * * *")
    public void sendTimeNotification() {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH시 mm분"));
        String message = currentTime + " 입니다.";

        messagingTemplate.convertAndSend("/topic/chat", message);
    }
}