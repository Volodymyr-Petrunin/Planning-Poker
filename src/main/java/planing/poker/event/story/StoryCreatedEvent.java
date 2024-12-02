package planing.poker.event.story;

import planing.poker.domain.dto.response.ResponseStoryDto;

public class StoryCreatedEvent extends StoryEvent {

    public StoryCreatedEvent(final ResponseStoryDto storyDto, final String roomCode) {
        super(storyDto, roomCode);
    }
}
