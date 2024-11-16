package planing.poker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(LoginController.BASE_URL)
public class LoginController {

    public static final String BASE_URL = "/login";

    public static final String LOGIN_PAGE = "login/login-page";

    @GetMapping
    public String login(@RequestParam(value = "error", required = false) final String error,
                        final Authentication authentication, final Model model) {
        if (authentication != null) {
            return "redirect:/";
        }

        model.addAttribute("error", error != null);

        return LOGIN_PAGE;
    }
}
