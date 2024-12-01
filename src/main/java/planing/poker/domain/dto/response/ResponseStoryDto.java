package planing.poker.domain.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

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

    public Integer averageVotePoints() {
        if (votes == null || votes.isEmpty()) {
            return null;
        }

        final int totalPoints = votes.stream()
                .mapToInt(ResponseVoteDto::getPoints)
                .sum();

        return totalPoints / votes.size();
    }
}