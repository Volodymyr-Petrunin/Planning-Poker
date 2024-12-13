package planing.poker.controller;

import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import planing.poker.domain.dto.update.UpdateUserDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.security.CustomUserDetailsService;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.UserService;

@Controller
@RequestMapping(UserProfileController.BASE_URL)
public class UserProfileController {

    public static final String BASE_URL = "/profile";

    public static final String SHOW_USER_PROFILE_PAGE = "/show/user/show-user-profile";

    private static final String USER = "user";

    private final UserService userService;

    private final CustomUserDetailsService customUserDetailsService;

    public UserProfileController(final UserService userService, final CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping
    public String profile(final Model model, @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        final ResponseUserDto user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute(USER, user);
        return SHOW_USER_PROFILE_PAGE;
    }

    @PostMapping
    public String updateProfile(@Valid @ModelAttribute(USER) final UpdateUserDto user, final BindingResult bindingResult,
                                final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(USER, user);
            return SHOW_USER_PROFILE_PAGE;
        }

        final ResponseUserDto responseUserDto = userService.updateUser(user);
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(responseUserDto.getEmail());
        updateAuthentication(userDetails);

        return SHOW_USER_PROFILE_PAGE;
    }

    private void updateAuthentication(final UserDetails userDetails) {
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
