package planing.poker.event.room;

import planing.poker.domain.dto.response.ResponseRoomDto;

public class RoomCurrentStoryEvent extends RoomEvent {

    public RoomCurrentStoryEvent(ResponseRoomDto roomDto) {
        super(roomDto);
    }
}
