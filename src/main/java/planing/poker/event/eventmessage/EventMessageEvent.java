package planing.poker.event.eventmessage;

import planing.poker.domain.dto.response.ResponseEventMessageDto;

public class EventMessageEvent {

    private final Long eventId;

    private final ResponseEventMessageDto responseEventMessageDto;

    public EventMessageEvent(final Long eventId, final ResponseEventMessageDto responseEventMessageDto) {
        this.eventId = eventId;
        this.responseEventMessageDto = responseEventMessageDto;
    }

    public ResponseEventMessageDto getResponseEventMessageDto() {
        return responseEventMessageDto;
    }

    public Long getEventId() {
        return eventId;
    }
}
