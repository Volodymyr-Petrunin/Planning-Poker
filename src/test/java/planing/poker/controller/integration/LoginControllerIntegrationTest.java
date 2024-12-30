package planing.poker.controller.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import planing.poker.controller.LoginController;
import planing.poker.security.WebSecurityConfiguration;
import planing.poker.security.WebSocketConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@Import({WebSocketConfig.class, WebSecurityConfiguration.class})
class LoginControllerIntegrationTest {

    private static final String BASE_URL = "/login";

    private static final String LOGIN_PAGE = "login/login-page";

    private static final String ERROR_ATTRIBUTE = "error";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void testLoginPageAccessibleForAnonymousUser() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN_PAGE))
                .andExpect(model().attribute(ERROR_ATTRIBUTE, false));
    }

    @Test
    @WithAnonymousUser
    void testLoginPageWithError() throws Exception {
        mockMvc.perform(get(BASE_URL).param(ERROR_ATTRIBUTE, "true"))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN_PAGE))
                .andExpect(model().attribute(ERROR_ATTRIBUTE, true));
    }

    @Test
    @WithMockUser
    void testLoginPageRedirectsForAuthenticatedUser() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
