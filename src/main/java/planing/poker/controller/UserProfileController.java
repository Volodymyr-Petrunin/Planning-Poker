package planing.poker.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import planing.poker.domain.dto.request.RequestUserDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.UserService;

@Controller
@RequestMapping(UserProfileController.BASE_URL)
public class UserProfileController {

    public static final String BASE_URL = "/profile";

    public static final String SHOW_USER_PROFILE_PAGE = "/show/user/show-user-profile";

    private static final String USER = "user";

    private final UserService userService;

    public UserProfileController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(final Model model, @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        final ResponseUserDto user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute(USER, user);
        return SHOW_USER_PROFILE_PAGE;
    }

    @PostMapping
    public String updateProfile(@ModelAttribute(USER) final RequestUserDto user, final BindingResult bindingResult,
                                final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(USER, user);
            return SHOW_USER_PROFILE_PAGE;
        }

//        userService.updateUser()
        return null;
    }

}
