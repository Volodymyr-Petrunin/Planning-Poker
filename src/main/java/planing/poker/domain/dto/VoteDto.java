package planing.poker.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planing.poker.domain.dto.response.ResponseUserDto;

/**
 * DTO for {@link planing.poker.domain.Vote}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {
    @Positive
    private Long id;

    @NotNull
    private ResponseUserDto voter;

    @NotNull
    private Integer points;

    @NotNull
    private StoryDto story;
}