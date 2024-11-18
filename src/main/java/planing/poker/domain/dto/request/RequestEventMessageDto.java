package planing.poker.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for {@link planing.poker.domain.EventMessage}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestEventMessageDto {

    @Positive
    @NotNull
    private Long eventId;

    @Positive
    @NotNull
    private Long userId;

    @NotBlank
    @NotNull
    private String messageKey;

    @NotBlank
    @NotNull
    private String messageArgs;

    @NotNull
    private LocalDateTime timestamp;
}
