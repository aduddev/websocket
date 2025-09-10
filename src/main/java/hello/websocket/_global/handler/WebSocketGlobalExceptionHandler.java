package hello.websocket._global.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class WebSocketGlobalExceptionHandler {

    // 모든 @MessageMapping 핸들러에서 발생하는 예외를 잡습니다.
    @MessageExceptionHandler(Exception.class)
    @SendToUser(destinations = "/queue/errors", broadcast = false)
    public String handleAllExceptions(Exception ex) {
        return "[시스템] : " + ex.getMessage();
    }

    // 유효성 검증 실패 시 호출되는 핸들러
    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendToUser(destinations = "/queue/errors", broadcast = false)
    public String handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError fieldError = result.getFieldError();
        String errorMessage = "[시스템] : 유효성 검증 오류";

        if (fieldError != null) {
            errorMessage = fieldError.getDefaultMessage();
        }

        return  "[시스템] : " + errorMessage;

    }

}