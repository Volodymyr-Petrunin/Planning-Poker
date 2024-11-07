package planing.poker.event.user;

import planing.poker.domain.dto.response.ResponseUserDto;

public class UserCreatedEvent extends UserEvent {

    public UserCreatedEvent(final ResponseUserDto responseUserDto) {
        super(responseUserDto);
    }
}
