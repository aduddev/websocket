package hello.websocket._global.config;

import hello.websocket._global.handler.CustomHandshakeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // webSocket 메세지 브로커 활성화
public class WebSocketStompBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // 클라이언트가 구독할 주소
        registry.setApplicationDestinationPrefixes("/app"); // @MessageMapping 경로 접두사
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/login/{username}") // 웹소켓 연결 엔드포인트
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new CustomHandshakeHandler());
//                .withSockJS(); // SockJS 지원
    }
}
