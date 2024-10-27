package planing.poker.event.story;

import planing.poker.domain.dto.response.ResponseStoryDto;

public class StoryCreatedEvent extends StoryEvent {

    public StoryCreatedEvent(ResponseStoryDto storyDto) {
        super(storyDto);
    }
}
