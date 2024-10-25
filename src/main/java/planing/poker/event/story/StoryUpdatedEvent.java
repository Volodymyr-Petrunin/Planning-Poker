package planing.poker.event.story;

import planing.poker.domain.dto.response.ResponseStoryDto;

public class StoryUpdatedEvent extends StoryEvent {

    public StoryUpdatedEvent(ResponseStoryDto storyDto) {
        super(storyDto);
    }
}
