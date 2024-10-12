package planing.poker.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import planing.poker.domain.dto.request.RequestUserDto;
import planing.poker.service.UserService;

@Controller
@RequestMapping(UserRegisterController.BASE_URL)
public class UserRegisterController {

    public static final String BASE_URL = "/register";

    private static final String LOGIN_PAGE_REDIRECT = "redirect:/login";

    private static final String USER_REGISTER_URL = "/user";

    private static final String USER_REGISTER_PAGE = "register/user/register_user";

    private static final String USER = "user";

    private final UserService userService;

    @Autowired
    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(USER_REGISTER_URL)
    public String regUser(final Model model, @ModelAttribute(USER) final RequestUserDto user) {
        model.addAttribute(USER, user);
        return USER_REGISTER_PAGE;
    }

    @PostMapping(USER_REGISTER_URL)
    public String regUser(@Valid @ModelAttribute(USER) final RequestUserDto user,
                          final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(USER, user);
            return USER_REGISTER_PAGE;
        }

        userService.createUser(user);
        return LOGIN_PAGE_REDIRECT;
    }
}
