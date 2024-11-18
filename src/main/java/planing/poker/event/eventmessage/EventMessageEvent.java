package planing.poker.event.eventmessage;

import planing.poker.domain.dto.EventDto;
import planing.poker.domain.dto.response.ResponseEventMessageDto;

public class EventMessageEvent {

    private final EventDto eventDto;

    private final ResponseEventMessageDto responseEventMessageDto;

    public EventMessageEvent(final EventDto eventDto, final ResponseEventMessageDto responseEventMessageDto) {
        this.eventDto = eventDto;
        this.responseEventMessageDto = responseEventMessageDto;
    }

    public ResponseEventMessageDto getResponseEventMessageDto() {
        return responseEventMessageDto;
    }

    public EventDto getEventDto() {
        return eventDto;
    }
}
