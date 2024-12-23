package planing.poker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

@DisplayName("Login Controller Test")
@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    private static final String LOGIN_PAGE = "login/login-page";

    private static final String REDIRECT_HOME = "redirect:/";

    private static final String ERROR_ATTRIBUTE = "error";

    @InjectMocks
    private LoginController loginController;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Test
    @DisplayName("Should return login page with error when error parameter is provided")
    void testLoginWithError() {
        final String error = "Invalid credentials";

        final String viewName = loginController.login(error, null, model);

        assertEquals(LOGIN_PAGE, viewName);
        verify(model).addAttribute(ERROR_ATTRIBUTE, true);
    }

    @Test
    @DisplayName("Should return login page without error when error parameter is not provided")
    void testLoginWithoutError() {
        final String viewName = loginController.login(null, null, model);

        assertEquals(LOGIN_PAGE, viewName);
        verify(model).addAttribute(ERROR_ATTRIBUTE, false);
    }

    @Test
    @DisplayName("Should redirect to home page when user is authenticated")
    void testLoginWhenAuthenticated() {
        final String viewName = loginController.login(null, authentication, model);

        assertEquals(REDIRECT_HOME, viewName);
        verifyNoInteractions(model);
    }
}
