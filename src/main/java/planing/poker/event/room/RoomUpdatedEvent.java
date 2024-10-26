package planing.poker.event.room;

import planing.poker.domain.dto.response.ResponseRoomDto;

public class RoomUpdatedEvent extends RoomEvent{

    public RoomUpdatedEvent(final ResponseRoomDto roomDto) {
        super(roomDto);
    }
}
