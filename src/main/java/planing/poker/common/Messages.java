package planing.poker.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/messages.properties")
public class Messages {

    private final String NO_FIND_MESSAGE;

    private final String CANNOT_CONVERT_MESSAGE;

    private final String INVALID_CODE_CONFIGURATION;

    @Autowired
    public Messages(@Value("${message.not.find.object}") final String NO_FIND_MESSAGE,
                    @Value("${message.cant.convert.string}") final String CANNOT_CONVERT_MESSAGE,
                    @Value("${message.invalid.code.configuration}") final String INVALID_CODE_CONFIGURATION) {
        this.NO_FIND_MESSAGE = NO_FIND_MESSAGE;
        this.CANNOT_CONVERT_MESSAGE = CANNOT_CONVERT_MESSAGE;
        this.INVALID_CODE_CONFIGURATION = INVALID_CODE_CONFIGURATION;
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
}
