package planing.poker.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should allow access to public endpoints")
    void shouldAllowAccessToPublicEndpoints() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/webjars/bootstrap/5.3.3/css/bootstrap.min.css"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should restrict access to authenticated endpoints")
    void shouldRestrictAccessToAuthenticatedEndpoints() throws Exception {
        mockMvc.perform(get("/room/some-room"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

        mockMvc.perform(get("/profile"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("Should allow access to login and registration endpoints")
    void shouldAllowAccessToLoginAndRegisterEndpoints() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/register/user"))
                .andExpect(status().isOk());
    }
}
