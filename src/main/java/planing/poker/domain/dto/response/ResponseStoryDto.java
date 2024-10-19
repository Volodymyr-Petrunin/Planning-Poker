package planing.poker.domain.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO for {@link planing.poker.domain.Story}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStoryDto {
    @Positive
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String storyLink;

    @NotNull
    private List<ResponseVoteDto> votes;
}