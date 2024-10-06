package planing.poker.factory.dto;

import planing.poker.domain.dto.StoryDto;
import planing.poker.domain.dto.UserDto;
import planing.poker.domain.dto.VoteDto;

public class VoteDtoFactory {
    private static final Long EXPECTED_ID = 1L;
    private static final Integer EXPECTED_POINTS = 5;

    public static VoteDto createVote(final UserDto voter, final StoryDto storyDto) {
        return new VoteDto(EXPECTED_ID, voter, EXPECTED_POINTS, storyDto);
    }
}
