package planing.poker.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.ui.Model;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Custom Error Controller Test")
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application.properties")
class CustomErrorControllerTest {

    private static final String ERROR_404_VIEW = "error/404";
    private static final String ERROR_500_VIEW = "error/500";
    private static final String GENERIC_ERROR_VIEW = "error/error";

    private static final String ATTRIBUTE_STATUS = "statusCode";
    private static final String ATTRIBUTE_RANDOM_IMAGE_INDEX = "randomImageIndex";

    private static final byte NUMBER_OF_PICTURES = 5;
    private static final String ATTRIBUTE_STATUS_CODE = "javax.servlet.error.status_code";

    private CustomErrorController customErrorController;

    private HttpServletRequest httpServletRequest;

    private Model model;

    private Random random;

    @BeforeEach
    void setUp() {
        random = mock(Random.class);
        customErrorController = new CustomErrorController(ATTRIBUTE_STATUS_CODE, NUMBER_OF_PICTURES, random);
        httpServletRequest = mock(HttpServletRequest.class);
        model = mock(Model.class);
    }

    @Test
    @DisplayName("Should handle 404 error")
    void testHandleError_404() {
        when(httpServletRequest.getAttribute(ATTRIBUTE_STATUS_CODE)).thenReturn(404);
        when(random.nextInt(NUMBER_OF_PICTURES)).thenReturn(2);

        final String viewName = customErrorController.handleError(httpServletRequest, model);

        assertEquals(ERROR_404_VIEW, viewName);
        verify(model).addAttribute(ATTRIBUTE_STATUS, 404);
        verify(model).addAttribute(ATTRIBUTE_RANDOM_IMAGE_INDEX, 2);
    }

    @Test
    @DisplayName("Should handle 500 error")
    void testHandleError_500() {
        when(httpServletRequest.getAttribute(ATTRIBUTE_STATUS_CODE)).thenReturn(500);
        when(random.nextInt(NUMBER_OF_PICTURES)).thenReturn(3);

        final String viewName = customErrorController.handleError(httpServletRequest, model);

        assertEquals(ERROR_500_VIEW, viewName);
        verify(model).addAttribute(ATTRIBUTE_STATUS, 500);
        verify(model).addAttribute(ATTRIBUTE_RANDOM_IMAGE_INDEX, 3);
    }

    @Test
    @DisplayName("Should handle unexpected error with null status code")
    void testHandleError_Unexpected() {
        when(httpServletRequest.getAttribute(ATTRIBUTE_STATUS_CODE)).thenReturn(null);
        when(random.nextInt(NUMBER_OF_PICTURES)).thenReturn(1);

        final String viewName = customErrorController.handleError(httpServletRequest, model);

        assertEquals(GENERIC_ERROR_VIEW, viewName);
        verify(model).addAttribute(ATTRIBUTE_STATUS, null);
        verify(model).addAttribute(ATTRIBUTE_RANDOM_IMAGE_INDEX, 1);
    }

    @Test
    @DisplayName("Should handle other status codes")
    void testHandleError_OtherStatusCode() {
        when(httpServletRequest.getAttribute(ATTRIBUTE_STATUS_CODE)).thenReturn(403);
        when(random.nextInt(NUMBER_OF_PICTURES)).thenReturn(0);

        final String viewName = customErrorController.handleError(httpServletRequest, model);

        assertEquals(GENERIC_ERROR_VIEW, viewName);
        verify(model).addAttribute(ATTRIBUTE_STATUS, 403);
        verify(model).addAttribute(ATTRIBUTE_RANDOM_IMAGE_INDEX, 0);
    }
}
