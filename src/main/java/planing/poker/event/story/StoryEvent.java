package planing.poker.event.story;

import lombok.Getter;
import planing.poker.domain.dto.response.ResponseStoryDto;

@Getter
public class StoryEvent {

    private final ResponseStoryDto storyDto;

    private final String roomCode;

    public StoryEvent(final ResponseStoryDto storyDto, final String roomCode) {
        this.storyDto = storyDto;
        this.roomCode = roomCode;
    }
}
