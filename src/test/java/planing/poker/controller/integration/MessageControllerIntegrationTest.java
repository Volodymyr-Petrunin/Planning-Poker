package planing.poker.controller.integration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import planing.poker.controller.MessageController;
import planing.poker.domain.dto.response.ResponseEventMessageDto;
import planing.poker.security.WebSecurityConfiguration;
import planing.poker.security.WebSocketConfig;
import planing.poker.service.EventMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(MessageController.class)
@DisplayName("Message Controller Integration Test")
@Import({WebSocketConfig.class, WebSecurityConfiguration.class})
class MessageControllerIntegrationTest {

    private static final String BASE_URL = "/messages/localize";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventMessageService eventMessageService;

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    @BeforeAll
    static void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Should return localized message with valid input")
    void testLocalizeMessage() throws Exception {
        final ResponseEventMessageDto dto = fixtureMonkey.giveMeOne(ResponseEventMessageDto.class);
        final String locale = "en";
        final String expectedMessage = "Localized Message";

        when(eventMessageService.getLocalizedMessage(any(ResponseEventMessageDto.class), any(Locale.class)))
                .thenReturn(expectedMessage);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", locale)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string(expectedMessage));

        verify(eventMessageService).getLocalizedMessage(any(ResponseEventMessageDto.class), eq(new Locale(locale)));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when input is invalid")
    void testLocalizeMessageInvalidInput() throws Exception {
        final String invalidJson = "{}";

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "en")
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(eventMessageService);
    }
}

