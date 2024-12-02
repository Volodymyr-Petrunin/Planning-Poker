package planing.poker.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

@Component
public class ThymeleafUtils {
    private final ObjectMapper objectMapper;

    public ThymeleafUtils(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
