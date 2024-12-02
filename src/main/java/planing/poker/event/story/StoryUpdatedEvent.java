package planing.poker.event.story;

import planing.poker.domain.dto.response.ResponseStoryDto;

public class StoryUpdatedEvent extends StoryEvent {

    public StoryUpdatedEvent(final ResponseStoryDto storyDto, final String roomCode) {
        super(storyDto, roomCode);
    }
}
