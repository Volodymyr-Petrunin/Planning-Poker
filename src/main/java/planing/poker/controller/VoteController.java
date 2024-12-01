package planing.poker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import planing.poker.domain.dto.request.RequestVoteDto;
import planing.poker.security.UserDetailsImpl;
import planing.poker.service.VoteService;

@Controller
public class VoteController {
    private static final String MESSAGE_MAPPING = "/sendVote";

    private static final String SEND_TO = "/topic/voteResult";

    private final VoteService voteService;

    @Autowired
    public VoteController(final VoteService voteService) {
        this.voteService = voteService;
    }

    @MessageMapping(MESSAGE_MAPPING)
    public void sendVote(final RequestVoteDto vote, final Authentication authentication) {
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        voteService.createVote(vote, userDetails.getUsername());
    }
}
