package planing.poker.event.user;

import planing.poker.domain.dto.response.ResponseUserDto;

public class UserChangeRoleEvent extends UserEvent{

    public UserChangeRoleEvent(final ResponseUserDto responseUserDto) {
        super(responseUserDto);
    }
}
