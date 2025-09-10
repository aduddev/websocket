package hello.websocket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


public class ChatRequest {
    @Getter
    @Setter
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Register {
        @NotBlank(message = "공백일 수 없습니다.")
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Message {
        @NotBlank(message = "공백일 수 없습니다.")
        private String content;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class PrivateMessage {
        @NotBlank(message = "공백일 수 없습니다.")
        private String content;
        @NotBlank(message = "공백일 수 없습니다.")
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class History {
        @NotBlank(message = "공백일 수 없습니다.")
        private String name;
    }
}
