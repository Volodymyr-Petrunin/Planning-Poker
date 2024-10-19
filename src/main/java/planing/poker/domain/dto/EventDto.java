package planing.poker.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planing.poker.domain.Event;
import planing.poker.domain.dto.response.ResponseRoomDto;

import java.util.List;

/**
 * DTO for {@link Event}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    @Positive
    private Long id;

    @NotNull
    private ResponseRoomDto room;

    @NotNull
    private List<EventMessageDto> eventMessages;
}