package planing.poker.event.eventmessage;

import planing.poker.domain.dto.response.ResponseEventMessageDto;

public class EventMessageCreatedEvent extends EventMessageEvent {

    public EventMessageCreatedEvent(final ResponseEventMessageDto responseEventMessageDto) {
        super(responseEventMessageDto);
    }
}
