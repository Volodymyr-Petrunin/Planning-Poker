package planing.poker.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO for {@link planing.poker.domain.Story}
 */
@Getter
@Setter
public class StoryDto {
    @Positive
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String storyLink;

    @NotNull
    private List<VoteDto> votes;
}