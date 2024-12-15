package planing.poker.event.user;

public class UserDeletedEvent extends UserEvent {

    private final long userId;

    public UserDeletedEvent(final long userId) {
        super(null);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
