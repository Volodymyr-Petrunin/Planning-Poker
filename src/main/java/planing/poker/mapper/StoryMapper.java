package planing.poker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import planing.poker.domain.Story;
import planing.poker.domain.dto.request.RequestStoryDto;
import planing.poker.domain.dto.response.ResponseStoryDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {VoteMapper.class})
public interface StoryMapper {
    Story toEntity(RequestStoryDto responseStoryDto);

    ResponseStoryDto toDto(Story story);

    Story responseDtoToEntity(ResponseStoryDto responseStoryDto);
}