package planing.poker.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planing.poker.domain.dto.response.ResponseUserDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link planing.poker.domain.EventMessage}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventMessageDto {
    @Positive
    private Long id;

    @NotNull
    private ResponseUserDto user;

    @NotNull
    private String message;

    @NotNull
    private LocalDateTime timestamp;
}