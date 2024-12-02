package planing.poker.event.room;

import lombok.Getter;
import planing.poker.domain.dto.response.ResponseRoomDto;

@Getter
public class RoomCurrentStoryEvent extends RoomEvent {

    private final String roomCode;

    public RoomCurrentStoryEvent(final ResponseRoomDto roomDto, final String roomCode) {
        super(roomDto);
        this.roomCode = roomCode;
    }
}
