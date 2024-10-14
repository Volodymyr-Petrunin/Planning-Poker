package planing.poker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import planing.poker.domain.Team;
import planing.poker.domain.dto.request.RequestTeamDto;
import planing.poker.domain.dto.response.ResponseTeamDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamMapper {

    Team toEntity(RequestTeamDto requestTeamDto);

    ResponseTeamDto toDto(Team team);
}
