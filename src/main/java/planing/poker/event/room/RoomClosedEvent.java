package planing.poker.event.room;

public class RoomClosedEvent extends RoomEvent{

    private Long roomId;

    public RoomClosedEvent(final Long roomId) {
        super(null);
        this.roomId = roomId;
    }

    public Long getRoomId() {
        return roomId;
    }
}
