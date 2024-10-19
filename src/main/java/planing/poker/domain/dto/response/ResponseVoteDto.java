package planing.poker.domain.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for {@link planing.poker.domain.Vote}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVoteDto {
    @Positive
    private Long id;

    @NotNull
    private ResponseUserDto voter;

    @NotNull
    private Integer points;

    @NotNull
    private ResponseStoryDto story;
}