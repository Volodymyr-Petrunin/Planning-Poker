package planing.poker.common;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalWebSocketExceptionHandler {

    private final SimpMessagingTemplate messagingTemplate;

    public GlobalWebSocketExceptionHandler(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageExceptionHandler({MethodArgumentNotValidException.class})
    public void handleValidationException(final MethodArgumentNotValidException ex, final SimpMessageHeaderAccessor accessor) {
        final String sessionId = accessor.getSessionId();
        final String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("Validation error");

        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(sessionId,"/alert/argument/not/valid",
                    errorMessage, getMessageHeaders(sessionId));
        }
    }

    private MessageHeaders getMessageHeaders(final String sessionId) {
        final SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
