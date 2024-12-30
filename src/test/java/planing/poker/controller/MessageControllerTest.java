package planing.poker.controller;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import planing.poker.domain.dto.response.ResponseEventMessageDto;
import planing.poker.service.EventMessageService;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Message Controller Test")
@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    private static final String BASE_URL = "/messages/localize";

    @InjectMocks
    private MessageController messageController;

    @Mock
    private EventMessageService eventMessageService;

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    @Test
    @DisplayName("Should return localized message with valid request")
    void testLocalizeMessageWithValidRequest() {
        final ResponseEventMessageDto dto = fixtureMonkey.giveMeOne(ResponseEventMessageDto.class);
        final Locale locale = Locale.ENGLISH;
        final String expectedMessage = "Localized Message";

        when(eventMessageService.getLocalizedMessage(dto, locale)).thenReturn(expectedMessage);

        final ResponseEntity<String> response = messageController.localizeMessage(dto, locale);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
        verify(eventMessageService).getLocalizedMessage(dto, locale);
    }
}
