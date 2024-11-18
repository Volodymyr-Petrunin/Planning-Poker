package planing.poker.event.eventmessage;

import planing.poker.domain.dto.response.ResponseEventMessageDto;

public class EventMessageEvent {
    private final ResponseEventMessageDto responseEventMessageDto;

    public EventMessageEvent(final ResponseEventMessageDto responseEventMessageDto) {
        this.responseEventMessageDto = responseEventMessageDto;
    }

    public ResponseEventMessageDto getResponseEventMessageDto() {
        return responseEventMessageDto;
    }
}
