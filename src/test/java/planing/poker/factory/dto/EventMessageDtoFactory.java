package planing.poker.factory.dto;

import planing.poker.domain.dto.EventMessageDto;
import planing.poker.domain.dto.response.ResponseUserDto;

import java.time.LocalDateTime;

public class EventMessageDtoFactory {
    private static final Long EXPECTED_ID = 1L;
    private static final String EXPECTED_MESSAGE = "Sample event message";
    private static final LocalDateTime EXPECTED_TIMESTAMP = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

    public static EventMessageDto createEventMessage(final ResponseUserDto responseUserDto) {
        return new EventMessageDto(EXPECTED_ID, responseUserDto, EXPECTED_MESSAGE, EXPECTED_TIMESTAMP);
    }
}
