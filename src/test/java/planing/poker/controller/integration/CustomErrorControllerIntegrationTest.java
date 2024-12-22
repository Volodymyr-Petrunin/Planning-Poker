package planing.poker.controller.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import planing.poker.controller.CustomErrorController;
import planing.poker.security.WebSecurityConfiguration;
import planing.poker.security.WebSocketConfig;

import java.util.Random;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomErrorController.class)
@Import({WebSocketConfig.class, WebSecurityConfiguration.class})
class CustomErrorControllerIntegrationTest {

    private static final String BASE_URL = "/error";

    private static final String ERROR_NOT_FOUND = "error/404";

    private static final String ERROR_INTERNAL_SERVER = "error/500";

    private static final String ERROR_UNEXPECTED = "error/error";

    private static final String STATUS_CODE_ATTRIBUTE = "statusCode";

    private static final String RANDOM_IMAGE_INDEX_ATTRIBUTE = "randomImageIndex";

    @Value("${error.status.code.attribute}")
    private String errorStatusCode;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Random random;

    @Test
    void testErrorPageFor404() throws Exception {
        mockMvc.perform(get(BASE_URL).requestAttr(errorStatusCode, 404))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_NOT_FOUND))
                .andExpect(model().attribute(STATUS_CODE_ATTRIBUTE, 404))
                .andExpect(model().attributeExists(RANDOM_IMAGE_INDEX_ATTRIBUTE));
    }

    @Test
    void testErrorPageFor500() throws Exception {
        mockMvc.perform(get(BASE_URL).requestAttr(errorStatusCode, 500))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_INTERNAL_SERVER))
                .andExpect(model().attribute(STATUS_CODE_ATTRIBUTE, 500))
                .andExpect(model().attributeExists(RANDOM_IMAGE_INDEX_ATTRIBUTE));
    }

    @Test
    void testErrorPageForUnexpectedError() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_UNEXPECTED))
                .andExpect(model().attribute(STATUS_CODE_ATTRIBUTE, nullValue()))
                .andExpect(model().attributeExists(RANDOM_IMAGE_INDEX_ATTRIBUTE));
    }

    @Test
    void testErrorPageForCustomStatusCode() throws Exception {
        mockMvc.perform(get(BASE_URL).requestAttr(errorStatusCode, 403))
                .andExpect(status().isOk())
                .andExpect(view().name(ERROR_UNEXPECTED))
                .andExpect(model().attribute(STATUS_CODE_ATTRIBUTE, 403))
                .andExpect(model().attributeExists(RANDOM_IMAGE_INDEX_ATTRIBUTE));
    }
}

