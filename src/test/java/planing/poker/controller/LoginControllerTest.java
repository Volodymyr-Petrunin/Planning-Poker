package planing.poker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    private LoginController loginController;

    private Model model;

    private Authentication authentication;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();
        model = mock(Model.class);
        authentication = mock(Authentication.class);
    }

    @Test
    void testLoginWithError() {
        String error = "Invalid credentials";

        String viewName = loginController.login(error, null, model);

        assertEquals("login/login-page", viewName);
        verify(model).addAttribute("error", true);
    }

    @Test
    void testLoginWithoutError() {
        String viewName = loginController.login(null, null, model);

        assertEquals("login/login-page", viewName);
        verify(model).addAttribute("error", false);
    }

    @Test
    void testLoginWhenAuthenticated() {
        when(authentication.isAuthenticated()).thenReturn(true);

        String viewName = loginController.login(null, authentication, model);

        assertEquals("redirect:/", viewName);
        verifyNoInteractions(model);
    }
}
