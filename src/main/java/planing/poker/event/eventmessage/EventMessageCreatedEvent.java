package planing.poker.event.eventmessage;

import planing.poker.domain.dto.response.ResponseEventMessageDto;

public class EventMessageCreatedEvent extends EventMessageEvent {

    public EventMessageCreatedEvent(final Long eventId, final ResponseEventMessageDto responseEventMessageDto) {
        super(eventId, responseEventMessageDto);
    }
}
