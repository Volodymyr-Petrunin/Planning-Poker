package planing.poker.factory.dto;

import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.domain.dto.response.ResponseVoteDto;

public class VoteDtoFactory {
    private static final Long EXPECTED_ID = 1L;
    private static final Integer EXPECTED_POINTS = 5;

    public static ResponseVoteDto createVote(final ResponseUserDto voter, final ResponseStoryDto responseStoryDto) {
        return new ResponseVoteDto(EXPECTED_ID, voter, EXPECTED_POINTS, responseStoryDto.getId());
    }
}
