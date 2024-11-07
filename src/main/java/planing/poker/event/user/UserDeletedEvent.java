package planing.poker.event.user;

import planing.poker.domain.dto.response.ResponseUserDto;

public class UserDeletedEvent extends UserEvent {

    private final long userId;

    public UserDeletedEvent(long userId) {
        super(null);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
