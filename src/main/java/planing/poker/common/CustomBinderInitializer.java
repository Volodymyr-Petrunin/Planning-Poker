package planing.poker.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomBinderInitializer {

    private final Messages messages;

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomBinderInitializer(final Messages messages,
                                   final ObjectMapper objectMapper) {
        this.messages = messages;
        this.objectMapper = objectMapper;
    }

    public <T> void registerCustomEditor(final WebDataBinder binder, final String fieldName, final Class<T[]> clazz) {
        binder.registerCustomEditor(List.class, fieldName, new PropertyEditorSupport() {
            @Override
            public void setAsText(final String text) throws IllegalArgumentException {
                try {
                    final List<T> values = Arrays.asList(objectMapper.readValue(text, clazz));
                    setValue(values);
                } catch (final Exception e) {
                    throw new IllegalArgumentException(messages.CANNOT_CONVERT_MESSAGE(), e);
                }
            }
        });
    }
}
