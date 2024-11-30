package planing.poker.common;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalWebSocketExceptionHandler {

    private static final String ARGUMENT_NOT_VALID_SEND_TO = "/alert/argument/not/valid";

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageSource messageSource;

    public GlobalWebSocketExceptionHandler(final SimpMessagingTemplate messagingTemplate,
                                           final MessageSource messageSource) {
        this.messagingTemplate = messagingTemplate;
        this.messageSource = messageSource;
    }

    @MessageExceptionHandler({MethodArgumentNotValidException.class})
    public void handleValidationException(final MethodArgumentNotValidException ex, final SimpMessageHeaderAccessor accessor) {
        final Locale locale = LocaleContextHolder.getLocale();
        final String sessionId = accessor.getSessionId();

        if (sessionId != null) {
            final List<String> errorMessage = getLocalizedErrorMessage(ex, locale);
            sendErrorMessageToUser(sessionId, errorMessage);
        }
    }

    private List<String> getLocalizedErrorMessage(final MethodArgumentNotValidException ex, final Locale locale) {
        final BindingResult result = ex.getBindingResult();

        return result.getAllErrors()
                .stream()
                .map(err -> messageSource.getMessage(err, locale))
                .toList();
    }

    private void sendErrorMessageToUser(final String sessionId, final List<String> errorMessage) {
        messagingTemplate.convertAndSendToUser(
                sessionId,
                ARGUMENT_NOT_VALID_SEND_TO,
                errorMessage,
                getMessageHeaders(sessionId)
        );
    }

    private MessageHeaders getMessageHeaders(final String sessionId) {
        final SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
