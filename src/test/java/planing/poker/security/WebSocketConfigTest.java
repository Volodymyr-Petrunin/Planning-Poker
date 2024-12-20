package planing.poker.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class WebSocketConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("STOMP endpoint /ws should be available")
    void stompEndpoint_ShouldBeAvailable() throws Exception {
        mockMvc.perform(get("/ws"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("STOMP endpoint with SockJS should respond")
    void stompEndpoint_WithSockJs_ShouldRespond() throws Exception {
        mockMvc.perform(options("/ws"))
                .andExpect(status().isOk());
    }
}