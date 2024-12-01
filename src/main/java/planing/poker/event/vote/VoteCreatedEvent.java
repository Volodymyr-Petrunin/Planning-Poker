package planing.poker.event.vote;

import planing.poker.domain.dto.response.ResponseVoteDto;

public class VoteCreatedEvent extends VoteEvent {

    private final String roomCode;

    public VoteCreatedEvent(final ResponseVoteDto responseVoteDto, final String roomCode) {
        super(responseVoteDto);
        this.roomCode = roomCode;
    }

    public String getRoomCode() {
        return roomCode;
    }
}
