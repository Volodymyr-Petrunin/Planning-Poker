package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.Vote;
import planing.poker.domain.dto.request.RequestVoteDto;
import planing.poker.domain.dto.response.ResponseVoteDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface VoteMapper {

    Vote toEntity(RequestVoteDto requestVoteDto);

    @Mapping(target = "storyId", source = "story.id")
    ResponseVoteDto toDto(Vote vote);
}