package planing.poker.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String ERROR_NOT_FOUND = "error/404";

    private static final String ERROR_INTERNAL_SERVER = "error/500";

    private static final String ERROR_UNEXPECTED = "error/error";

    private final String attributeStatusCode;

    @Autowired
    public CustomErrorController(@Value("${error.status.code.attribute}") final String attributeStatusCode) {
        this.attributeStatusCode = attributeStatusCode;
    }

    @RequestMapping("/error")
    public String handleError(final HttpServletRequest request, final Model model) {
        final Object statusCode = request.getAttribute(attributeStatusCode);
        model.addAttribute("statusCode", statusCode);

        if (statusCode != null) {
            int status = Integer.parseInt(statusCode.toString());

            if (status == 404) {
                return ERROR_NOT_FOUND;
            } else if (status == 500) {
                return ERROR_INTERNAL_SERVER;
            }
        }

        return ERROR_UNEXPECTED;
    }
}

