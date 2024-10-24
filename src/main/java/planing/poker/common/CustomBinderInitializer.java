package planing.poker.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.domain.dto.response.ResponseUserDto;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.List;

@Component
@PropertySource(value = "classpath:/messages.properties")
public class CustomBinderInitializer implements WebBindingInitializer {

    private final String message;

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomBinderInitializer(@Value("${message.cant.convert.string}") final String message,
                                   final ObjectMapper objectMapper) {
        this.message = message;
        this.objectMapper = objectMapper;
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        registerCustomEditor(binder, "invitedUsers", ResponseUserDto[].class);
        registerCustomEditor(binder, "stories", RequestStoryDto[].class);
        registerCustomEditor(binder, "story", ResponseStoryDto[].class);
    }

    private <T> void registerCustomEditor(final WebDataBinder binder, final String fieldName, final Class<T[]> clazz) {
        binder.registerCustomEditor(List.class, fieldName, new PropertyEditorSupport() {
            @Override
            public void setAsText(final String text) throws IllegalArgumentException {
                try {
                    final List<T> values = Arrays.asList(objectMapper.readValue(text, clazz));
                    setValue(values);
                } catch (final Exception e) {
                    throw new IllegalArgumentException(message, e);
                }
            }
        });
    }
}
