package hello.websocket.controller;

import hello.websocket.dto.ChatRequest;
import hello.websocket.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    final ChatService chatService;

    /**
     * 채팅 메시지 전송
     */
    @MessageMapping("/chat/send")
    public void sendMessage(Principal principal,
                            @Payload @Valid ChatRequest.Message req) {
        chatService.send(principal, req);

    }

    /**
     * 채팅 내역 조회
     */
    @MessageMapping("/chat/history")
    public void getHistory(Principal principal,
                           @Payload @Valid ChatRequest.History req) {
        chatService.getHistory(principal, req);
    }
}