package planing.poker.event.eventmessage;

import planing.poker.domain.dto.EventDto;
import planing.poker.domain.dto.response.ResponseEventMessageDto;

public class EventMessageCreatedEvent extends EventMessageEvent {

    public EventMessageCreatedEvent(final EventDto eventDto, final ResponseEventMessageDto responseEventMessageDto) {
        super(eventDto, responseEventMessageDto);
    }
}
