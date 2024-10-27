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

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public StoryEventListener(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleStoryCreated(final StoryCreatedEvent event) {
        messagingTemplate.convertAndSend("/topic/storyCreated", event.getStoryDto());
    }

    @EventListener
    public void handleStoryUpdated(final StoryUpdatedEvent event) {
        messagingTemplate.convertAndSend("/topic/storyUpdated", event.getStoryDto());
    }

    @EventListener
    public void handleStoryDeleted(final StoryDeletedEvent event) {
        messagingTemplate.convertAndSend("/topic/storyDeleted", event.getStoryId());
    }
}
