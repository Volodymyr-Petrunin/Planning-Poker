package planing.poker.event.room;

public class RoomDeletedEvent extends RoomEvent {

    private final long roomId;

    public RoomDeletedEvent(final long roomId) {
        super(null);
        this.roomId = roomId;
    }

    public long getRoomId() {
        return roomId;
    }
}
