package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.Vote;
import planing.poker.domain.dto.VoteDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface VoteMapper {
    Vote toEntity(VoteDto voteDto);

    VoteDto toDto(Vote vote);
}