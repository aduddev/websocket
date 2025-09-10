package hello.websocket.controller;

import hello.websocket.dto.ChatRequest;
import hello.websocket.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PersonalChatController {

    final ChatService chatService;
    /**
     * 1대1 채팅 메시지 전송
     */
    @MessageMapping("/chat/private/send")
    public void sendMessage(Principal principal,
                            @Payload @Valid ChatRequest.PrivateMessage req,
                            SimpMessageHeaderAccessor headerAccessor) {
        chatService.sendMessage(principal, req, headerAccessor);
    }

    /**
     * 채팅 내역 조회 (개인 메시지)
     */
    @MessageMapping("/chat/private/history")
    public void getPrivateHistory(Principal principal,
                                  ChatRequest.History req) {
        chatService.getPrivateHistory(principal, req);
    }
}