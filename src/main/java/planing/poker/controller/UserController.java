package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import planing.poker.common.Role;
import planing.poker.domain.dto.request.RequestRoomUserRoleDto;
import planing.poker.domain.dto.request.RequestUserDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private static final String SEARCH_USERS_MESSAGE_MAPPING = "/searchUsers";

    private static final String  SEARCH_USERS_SEND_TO = "/topic/userResults";

    private static final String  CHANGE_USER_ROLE_MESSAGE_MAPPING = "/changeUserRole";

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @MessageMapping(SEARCH_USERS_MESSAGE_MAPPING)
    @SendTo(SEARCH_USERS_SEND_TO)
    public List<ResponseUserDto> findUsers(final RequestUserDto user, final Authentication principal) {
        final UserDetails userDetails = (UserDetails) principal.getPrincipal();

        final List<ResponseUserDto> users = new ArrayList<>(userService.getUsersByExample(user));
        users.removeIf(currentUser -> currentUser.getEmail().equals(userDetails.getUsername()));

        return users;
    }

    @MessageMapping(CHANGE_USER_ROLE_MESSAGE_MAPPING)
    public void changeUserRole(final RequestRoomUserRoleDto request) {
        userService.updateUserRole(request.getRoomId(), request.getUserId(), Role.valueOf(request.getNewRole()));
    }
}
