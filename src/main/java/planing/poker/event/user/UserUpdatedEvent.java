package planing.poker.event.user;

import planing.poker.domain.dto.response.ResponseUserDto;

public class UserUpdatedEvent extends UserEvent{

    public UserUpdatedEvent(final ResponseUserDto responseUserDto) {
        super(responseUserDto);
    }
}
