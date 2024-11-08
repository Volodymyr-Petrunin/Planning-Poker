package planing.poker.event.user;

import planing.poker.domain.dto.response.ResponseUserDto;

public class UserEvent {

    private final ResponseUserDto responseUserDto;

    public UserEvent(final ResponseUserDto responseUserDto) {
        this.responseUserDto = responseUserDto;
    }

    public ResponseUserDto getResponseUserDto() {
        return responseUserDto;
    }
}
