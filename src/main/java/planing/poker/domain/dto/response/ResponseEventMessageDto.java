package planing.poker.domain.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * DTO for {@link planing.poker.domain.EventMessage}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseEventMessageDto {
    @Positive
    private Long id;

    @NotNull
    private ResponseUserDto user;

    @NotBlank
    @NotNull
    private String messageKey;

    @NotBlank
    @NotNull
    private String messageArgs;

    @NotNull
    private LocalDateTime timestamp;
}