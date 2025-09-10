package hello.websocket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisChatService {

    final RedisTemplate<String, String> redisTemplate;

    /**
     * 채팅 메세지 저장
     */
    public void saveMessage(String sender, String content) {
        String messageKey = "chat:" + sender;
        redisTemplate.opsForList().rightPush(messageKey, content);
    }

    /**
     * 채팅 메세지 조회
     */
    public List<String> getChatHistory(String sender) {
        String messageKey = "chat:" + sender;
        return redisTemplate.opsForList().range(messageKey, 0, -1);
    }

    /**
     * 1:1 채팅 메세지 저장
     */
    public void saveMessage(String sender, String recipient, String message) {
        String roomId = getChatRoomId(sender, recipient);

        redisTemplate.opsForList().leftPush(roomId, sender + " : " + message);
    }

    /**
     * 1:1 채팅 메세지 조회
     */
    public List<String> getRoomIdHistory(String user1, String user2) {
        String roomId = getChatRoomId(user1, user2);
        return redisTemplate.opsForList().range(roomId, 0, -1);
    }


    public String getChatRoomId(String user1, String user2) {
        String[] users = {user1, user2};
        Arrays.sort(users);

        return users[0] + ":" + users[1];
    }

}