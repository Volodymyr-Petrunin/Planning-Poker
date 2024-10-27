package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import planing.poker.domain.dto.request.RequestUserDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private static final String MESSAGE_MAPPING = "/searchUsers";

    private static final String SEND_TO = "/topic/userResults";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @MessageMapping(MESSAGE_MAPPING)
    @SendTo(SEND_TO)
    public List<ResponseUserDto> findUsers(final RequestUserDto user) {
        return userService.getUsersByExample(user);
    }
}
