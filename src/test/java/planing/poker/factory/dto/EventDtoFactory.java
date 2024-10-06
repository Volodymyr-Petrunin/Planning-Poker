package planing.poker.factory.dto;

import planing.poker.domain.dto.EventDto;
import planing.poker.domain.dto.EventMessageDto;
import planing.poker.domain.dto.RoomDto;

import java.util.List;

public class EventDtoFactory {
    private static final Long EXPECTED_ID = 1L;

    public static EventDto createEvent(final RoomDto roomDto, final List<EventMessageDto> eventMessages) {
        return new EventDto(EXPECTED_ID, roomDto, eventMessages);
    }
}
