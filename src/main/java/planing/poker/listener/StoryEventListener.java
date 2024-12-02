package planing.poker.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import planing.poker.event.story.StoryCreatedEvent;
import planing.poker.event.story.StoryDeletedEvent;
import planing.poker.event.story.StoryUpdatedEvent;

@Component
public class StoryEventListener {

    private static final String TOPIC_STORY_CREATED_TEMPLATE = "/topic/storyCreated/%s";

    private static final String TOPIC_STORY_UPDATED_TEMPLATE = "/topic/storyUpdated/%s";

    private static final String TOPIC_STORY_DELETED_TEMPLATE = "/topic/storyDeleted/%s";

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public StoryEventListener(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleStoryCreated(final StoryCreatedEvent event) {
        messagingTemplate.convertAndSend(
                createTopic(TOPIC_STORY_CREATED_TEMPLATE, event.getRoomCode()), event.getStoryDto());
    }

    @EventListener
    public void handleStoryUpdated(final StoryUpdatedEvent event) {
        messagingTemplate.convertAndSend(
                createTopic(TOPIC_STORY_UPDATED_TEMPLATE, event.getRoomCode()), event.getStoryDto());
    }

    @EventListener
    public void handleStoryDeleted(final StoryDeletedEvent event) {
        messagingTemplate.convertAndSend(
                createTopic(TOPIC_STORY_DELETED_TEMPLATE, event.getRoomCode()), event.getStoryId());
    }

    private String createTopic(final String template, final String... strings){
        return String.format(template, strings);
    }
}
