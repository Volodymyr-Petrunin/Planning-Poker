package planing.poker.controller.integration.websoket;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import planing.poker.controller.common.WithCustomAuthenticationPrincipal;
import planing.poker.domain.SecurityRole;
import planing.poker.domain.dto.request.RequestVoteDto;
import planing.poker.security.WebSecurityConfiguration;
import planing.poker.service.VoteService;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({WebSocketTestConfiguration.class, WebSecurityConfiguration.class})
class VoteControllerIntegrationTest {

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    @LocalServerPort
    private int port;

    @Autowired
    private WebSocketStompClient stompClient;

    @MockBean
    private VoteService voteService;

    @Test
    @DisplayName("Send vote should be accessible for authorized user via SockJS")
    @WithCustomAuthenticationPrincipal(username = "user@example.com", password = "password", role = SecurityRole.ROLE_ADMIN)
    void testSendVote_shouldBeAccessibleForAuthorizedUserViaSockJS() throws Exception {
        // URL for SockJS
        final String websocketUrl = "http://localhost:" + port + "/ws";

        // Create Session
        final StompSession session = stompClient
                .connect(websocketUrl, new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);

        // Test data
        final RequestVoteDto vote = fixtureMonkey.giveMeOne(RequestVoteDto.class);
        session.send("/app/sendVote", vote);

        // TODO figure out why Authentication is null in controller

        // Verify block
        verify(voteService).createVote(eq(vote), anyString());
    }
}
