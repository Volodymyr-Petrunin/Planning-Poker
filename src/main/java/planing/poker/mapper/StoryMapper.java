package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.Story;
import planing.poker.domain.dto.StoryDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {VoteMapper.class})
public interface StoryMapper {
    Story toEntity(StoryDto storyDto);

    @AfterMapping
    default void linkVotes(@MappingTarget Story story) {
        story.getVotes().forEach(vote -> vote.setStory(story));
    }

    StoryDto toDto(Story story);
}