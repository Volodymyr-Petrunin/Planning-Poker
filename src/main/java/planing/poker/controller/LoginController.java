package planing.poker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(LoginController.BASE_URL)
public class LoginController {

    public static final String BASE_URL = "/login";

    public static final String LOGIN_PAGE = "/login/login";

    @GetMapping
    public String login(final Authentication authentication) {
        return authentication != null ? "redirect:/" : LOGIN_PAGE;
    }
}
