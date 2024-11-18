package planing.poker.factory.dto;

import planing.poker.domain.dto.EventDto;
import planing.poker.domain.dto.response.ResponseEventMessageDto;
import planing.poker.domain.dto.response.ResponseRoomDto;

import java.util.List;

public class EventDtoFactory {
    private static final Long EXPECTED_ID = 1L;

    public static EventDto createEvent(final ResponseRoomDto responseRoomDto, final List<ResponseEventMessageDto> eventMessages) {
        return new EventDto(EXPECTED_ID, responseRoomDto, eventMessages);
    }
}
