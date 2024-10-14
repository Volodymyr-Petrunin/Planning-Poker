package planing.poker.factory.dto;

import planing.poker.domain.dto.response.ResponseTeamDto;
import planing.poker.domain.dto.response.ResponseUserDto;

import java.util.List;

public class TeamDtoFactory {

    private static final Long TEAM_ID = 1L;

    private static final String TEAM_NAME = "Best Team";

    public static ResponseTeamDto createResponseTeamDto(final ResponseUserDto leader, final List<ResponseUserDto> members){
        return new ResponseTeamDto(TEAM_ID, TEAM_NAME, leader, members);
    }
}
