package planing.poker.event.story;

public class StoryDeletedEvent extends StoryEvent {

    private final long storyId;

    public StoryDeletedEvent(long storyId) {
        super(null);
        this.storyId = storyId;
    }

    public long getStoryId() {
        return storyId;
    }
}
