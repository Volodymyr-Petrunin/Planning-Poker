package planing.poker.event.room;

import lombok.Getter;
import planing.poker.domain.dto.response.ResponseRoomDto;

@Getter
public class RoomVotingEvent extends RoomEvent {

    private final String roomCode;

    public RoomVotingEvent(final ResponseRoomDto roomDto, final String roomCode) {
        super(roomDto);
        this.roomCode = roomCode;
    }
}
