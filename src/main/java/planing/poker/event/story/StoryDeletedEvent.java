package planing.poker.event.story;

import lombok.Getter;

@Getter
public class StoryDeletedEvent extends StoryEvent {

    private final long storyId;

    public StoryDeletedEvent(final long storyId, final String roomCode) {
        super(null, roomCode);
        this.storyId = storyId;
    }
}
