package planing.poker.event.vote;

import planing.poker.domain.dto.response.ResponseVoteDto;

public class VoteEvent {

    private ResponseVoteDto responseVoteDto;

    public VoteEvent(final ResponseVoteDto responseVoteDto) {
        this.responseVoteDto = responseVoteDto;
    }

    public ResponseVoteDto getResponseVoteDto() {
        return responseVoteDto;
    }
}
