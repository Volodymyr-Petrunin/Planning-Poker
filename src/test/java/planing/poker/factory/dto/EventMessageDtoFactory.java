package planing.poker.factory.dto;

import planing.poker.domain.dto.EventMessageDto;
import planing.poker.domain.dto.UserDto;

import java.time.LocalDateTime;

public class EventMessageDtoFactory {
    private static final Long EXPECTED_ID = 1L;
    private static final String EXPECTED_MESSAGE = "Sample event message";
    private static final LocalDateTime EXPECTED_TIMESTAMP = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

    public static EventMessageDto createEventMessage(final UserDto userDto) {
        return new EventMessageDto(EXPECTED_ID, userDto, EXPECTED_MESSAGE, EXPECTED_TIMESTAMP);
    }
}
