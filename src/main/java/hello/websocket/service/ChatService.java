package hello.websocket.service;

import hello.websocket.dto.ChatRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    final RedisChatService redisChatService;
    final SimpMessagingTemplate messagingTemplate;

    final static String TOPIC_URL = "/topic/chat";
    final static String QUEUE_URL = "/queue/chat";

    final static String QUEUE_PRIVATE_URL = "/queue/room";

    /**
     * 메세지 보내기 및 저장
     */
    public void send(Principal principal, ChatRequest.Message req) {

        String sender = principal.getName();

        redisChatService.saveMessage(sender, req.getContent());

        messagingTemplate.convertAndSend(TOPIC_URL, sender + ": " + req.getContent());
    }

    /**
     * 특정 유저 채팅 내역 조회
     */
    public void getHistory(Principal principal, ChatRequest.History req) {
        List<String> histories = redisChatService.getChatHistory(req.getName());

        String sessionId = principal.getName();
        messagingTemplate.convertAndSendToUser(sessionId, QUEUE_URL, histories);
    }

    /**
     * 1:1 대화한 유저 채팅 내역 조회
     */
    public void getPrivateHistory(Principal principal, ChatRequest.History req) {
        String sender = principal.getName();
        String receiver = req.getName();
        messagingTemplate.convertAndSendToUser(sender, QUEUE_PRIVATE_URL, redisChatService.getRoomIdHistory(sender, receiver));
    }

    /**
     * 1:1 대화
     */
    public void sendMessage(Principal principal, ChatRequest.@Valid PrivateMessage req, SimpMessageHeaderAccessor headerAccessor) {
        String sender = principal.getName();
        String receiver = req.getName();
        String content = req.getContent();

        redisChatService.saveMessage(sender, receiver, content);

        messagingTemplate.convertAndSendToUser(receiver, QUEUE_PRIVATE_URL, content);
        messagingTemplate.convertAndSendToUser(sender, QUEUE_PRIVATE_URL, content);
    }
}