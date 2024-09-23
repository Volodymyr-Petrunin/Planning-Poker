package planing.poker.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for {@link planing.poker.domain.EventMessage}
 */

@Getter
@Setter
public class EventMessageDto {
    @Positive
    private Long id;

    @NotNull
    private UserDto user;

    @NotNull
    private String message;

    @NotNull
    private LocalDateTime timestamp;
}