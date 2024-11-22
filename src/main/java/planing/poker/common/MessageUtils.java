package planing.poker.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtils {

    private final MessageSource messageSource;

    @Autowired
    public MessageUtils(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(final String key, final Locale locale, final Object... args) {
        return messageSource.getMessage(key, args, locale);
    }
}
