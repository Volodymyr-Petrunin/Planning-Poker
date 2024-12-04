package planing.poker.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.domain.dto.response.ResponseUserDto;

/**
 * DTO for {@link planing.poker.domain.Vote}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestVoteDto {

    private ResponseUserDto voter;

    @NotNull
    private Integer points;

    @NotNull
    private ResponseStoryDto story;

    @NotNull
    @NotBlank
    private String roomCode;

    @NotNull
    private Boolean isAnonymousVoting;

    @NotNull
    private Long eventId;
}
