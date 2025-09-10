package hello.websocket._global.handler;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes){
        String path = request.getURI().getPath();
        String[] segments = path.split("/");

        if (segments.length >= 2 && "login".equals(segments[segments.length - 2])) {
            String username = segments[segments.length - 1];

            if (username != null && !username.isEmpty()) {
                return () -> username;
            }
        }

        return null;
    }
}