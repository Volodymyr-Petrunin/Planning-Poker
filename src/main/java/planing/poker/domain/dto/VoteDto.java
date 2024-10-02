package planing.poker.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for {@link planing.poker.domain.Vote}
 */

@Getter
@Setter
public class VoteDto {
    @Positive
    private Long id;

    @NotNull
    private UserDto voter;

    @NotNull
    private Integer points;

    @NotNull
    private StoryDto story;
}