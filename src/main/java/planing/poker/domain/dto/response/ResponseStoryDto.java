package planing.poker.domain.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

/**
 * DTO for {@link planing.poker.domain.Story}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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