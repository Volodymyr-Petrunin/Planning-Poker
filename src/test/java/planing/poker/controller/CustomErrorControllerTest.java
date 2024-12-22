package planing.poker.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomErrorControllerTest {

    private static final String ATTRIBUTE_STATUS_CODE = "javax.servlet.error.status_code";

    private static final byte NUMBER_OF_PICTURES = 5;

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
    void testHandleError_404() {
        when(httpServletRequest.getAttribute(ATTRIBUTE_STATUS_CODE)).thenReturn(404);
        when(random.nextInt(NUMBER_OF_PICTURES)).thenReturn(2);

        String viewName = customErrorController.handleError(httpServletRequest, model);

        assertEquals("error/404", viewName);
        verify(model).addAttribute("statusCode", 404);
        verify(model).addAttribute("randomImageIndex", 2);
    }

    @Test
    void testHandleError_500() {
        when(httpServletRequest.getAttribute(ATTRIBUTE_STATUS_CODE)).thenReturn(500);
        when(random.nextInt(NUMBER_OF_PICTURES)).thenReturn(3);

        String viewName = customErrorController.handleError(httpServletRequest, model);

        assertEquals("error/500", viewName);
        verify(model).addAttribute("statusCode", 500);
        verify(model).addAttribute("randomImageIndex", 3);
    }

    @Test
    void testHandleError_Unexpected() {
        when(httpServletRequest.getAttribute(ATTRIBUTE_STATUS_CODE)).thenReturn(null);
        when(random.nextInt(NUMBER_OF_PICTURES)).thenReturn(1);

        String viewName = customErrorController.handleError(httpServletRequest, model);

        assertEquals("error/error", viewName);
        verify(model).addAttribute("statusCode", null);
        verify(model).addAttribute("randomImageIndex", 1);
    }

    @Test
    void testHandleError_OtherStatusCode() {
        when(httpServletRequest.getAttribute(ATTRIBUTE_STATUS_CODE)).thenReturn(403);
        when(random.nextInt(NUMBER_OF_PICTURES)).thenReturn(0);

        String viewName = customErrorController.handleError(httpServletRequest, model);

        assertEquals("error/error", viewName);
        verify(model).addAttribute("statusCode", 403);
        verify(model).addAttribute("randomImageIndex", 0);
    }
}
