package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import planing.poker.domain.dto.request.RequestVoteDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.UserService;
import planing.poker.service.VoteService;

@Controller
public class VoteController {
    private static final String MESSAGE_MAPPING = "/sendVote";

    private static final String SEND_TO = "/topic/voteResult";

    private final VoteService voteService;

    private final UserService userService;

    @Autowired
    public VoteController(final VoteService voteService, final UserService userService) {
        this.voteService = voteService;
        this.userService = userService;
    }

    @MessageMapping(MESSAGE_MAPPING)
    @SendTo(SEND_TO)
    public void sendVote(final RequestVoteDto vote, final Authentication authentication) {
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final ResponseUserDto user = userService.getUserByEmail(userDetails.getUsername());
        vote.setVoter(user);

        voteService.createVote(vote);
    }
}
