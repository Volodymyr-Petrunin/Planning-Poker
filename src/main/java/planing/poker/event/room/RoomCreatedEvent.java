package planing.poker.event.room;

import planing.poker.domain.dto.response.ResponseRoomDto;

public class RoomCreatedEvent extends RoomEvent{

    public RoomCreatedEvent(final ResponseRoomDto roomDto) {
        super(roomDto);
    }
}
