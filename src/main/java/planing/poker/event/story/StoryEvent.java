package planing.poker.event.story;

import planing.poker.domain.dto.response.ResponseStoryDto;

public class StoryEvent {

    private final ResponseStoryDto storyDto;

    public StoryEvent(ResponseStoryDto storyDto) {
        this.storyDto = storyDto;
    }

    public ResponseStoryDto getStoryDto() {
        return storyDto;
    }
}
