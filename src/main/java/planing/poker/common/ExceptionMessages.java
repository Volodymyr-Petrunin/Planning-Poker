package planing.poker.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/messages.properties")
public class ExceptionMessages {

    private final String NO_FIND_MESSAGE;

    private final String CANNOT_CONVERT_MESSAGE;

    private final String INVALID_CODE_CONFIGURATION;

    private final String CANNOT_FIND_ROOM;

    @Autowired
    public ExceptionMessages(@Value("${message.not.find.object}") final String NO_FIND_MESSAGE,
                             @Value("${message.cant.convert.string}") final String CANNOT_CONVERT_MESSAGE,
                             @Value("${message.invalid.code.configuration}") final String INVALID_CODE_CONFIGURATION,
                             @Value("${message.cant.find.room}") final String CANNOT_FIND_ROOM) {
        this.NO_FIND_MESSAGE = NO_FIND_MESSAGE;
        this.CANNOT_CONVERT_MESSAGE = CANNOT_CONVERT_MESSAGE;
        this.INVALID_CODE_CONFIGURATION = INVALID_CODE_CONFIGURATION;
        this.CANNOT_FIND_ROOM = CANNOT_FIND_ROOM;
    }

    public String NO_FIND_MESSAGE() {
        return NO_FIND_MESSAGE;
    }

    public String CANNOT_CONVERT_MESSAGE() {
        return CANNOT_CONVERT_MESSAGE;
    }

    public String INVALID_CODE_CONFIGURATION() {
        return INVALID_CODE_CONFIGURATION;
    }

    public String CANNOT_FIND_ROOM() {
        return CANNOT_FIND_ROOM;
    }
}
