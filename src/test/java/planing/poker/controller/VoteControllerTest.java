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
import org.springframework.security.core.Authentication;
import planing.poker.domain.dto.request.RequestVoteDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.VoteService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Vote Controller Unit Test")
@ExtendWith(MockitoExtension.class)
class VoteControllerTest {

    @InjectMocks
    private VoteController voteController;

    @Mock
    private VoteService voteService;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsImpl userDetails;

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    @Test
    @DisplayName("Should call voteService with correct parameters")
    void testSendVote() {
        final RequestVoteDto vote = fixtureMonkey.giveMeOne(RequestVoteDto.class);
        final String username = "testUser";

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);

        voteController.sendVote(vote, authentication);

        verify(voteService).createVote(vote, username);
    }
}
