package planing.poker.event.room;

import planing.poker.domain.dto.response.ResponseRoomDto;

public class RoomEvent {

    private final ResponseRoomDto roomDto;

    public RoomEvent(final ResponseRoomDto roomDto) {
        this.roomDto = roomDto;
    }

    public ResponseRoomDto getRoomDto() {
        return roomDto;
    }
}
