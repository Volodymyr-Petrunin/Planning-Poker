package planing.poker.security;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import planing.poker.domain.User;
import planing.poker.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Custom User Details Service Tests")
class CustomUserDetailsServiceTest {

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static User expectedUser;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeAll
    static void setUp() {
        expectedUser = fixtureMonkey.giveMeOne(User.class);
    }

    @Test
    @DisplayName("Test should load user by email")
    void testLoadUserByUsername_ShouldLoadUserByEmail_AndReturnUserDetails() {
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(expectedUser);

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(expectedUser.getEmail());

        assertNotNull(userDetails);
        assertEquals(expectedUser.getEmail(), userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail(expectedUser.getEmail());
    }

    @Test
    @DisplayName("Test should throw exception when user not found")
    void testLoadUserByUsername_ShouldThrowException_WhenUserNotFound() {
        final String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(email));

        verify(userRepository, times(1)).findByEmail(email);
    }

}