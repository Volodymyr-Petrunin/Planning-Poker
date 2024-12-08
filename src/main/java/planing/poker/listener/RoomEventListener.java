package planing.poker.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import planing.poker.event.room.*;

@Component
public class RoomEventListener {

    public static final String TOPIC_ROOM_CREATED = "/topic/roomCreated";

    public static final String TOPIC_ROOM_UPDATED = "/topic/roomUpdated";

    public static final String TOPIC_ROOM_DELETED = "/topic/roomDeleted";

    public static final String TOPIC_ROOM_CLOSED = "/topic/roomClosed";

    public static final String TOPIC_UPDATE_CURRENT_STORY_TEMPLATE = "/topic/updateCurrentStory/%s";

    public static final String TOPIC_ROOM_VOTING_TEMPLATE = "/topic/updateVoting/%s";

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public RoomEventListener(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleRoomCreated(final RoomCreatedEvent event) {
        messagingTemplate.convertAndSend(TOPIC_ROOM_CREATED, event.getRoomDto());
    }

    @EventListener
    public void handleRoomUpdated(final RoomUpdatedEvent event) {
        messagingTemplate.convertAndSend(TOPIC_ROOM_UPDATED, event.getRoomDto());
    }

    @EventListener
    public void handleRoomDeleted(final RoomDeletedEvent event) {
        messagingTemplate.convertAndSend(TOPIC_ROOM_DELETED, event.getRoomId());
    }

    @EventListener
    public void handleRoomClosed(final RoomClosedEvent event) {
        messagingTemplate.convertAndSend(TOPIC_ROOM_CLOSED, event.getRoomId());
    }

    @EventListener
    public void handleRoomCurrentStoryUpdate(final RoomCurrentStoryEvent event) {
        messagingTemplate.convertAndSend(
                createTopic(TOPIC_UPDATE_CURRENT_STORY_TEMPLATE, event.getRoomCode()), event.getRoomDto());
    }

    @EventListener
    public void handleRoomVoting(final RoomVotingEvent event) {
        messagingTemplate.convertAndSend (
                createTopic(TOPIC_ROOM_VOTING_TEMPLATE, event.getRoomCode()), event.getRoomDto());
    }

    private String createTopic(final String template, final String... strings){
        return String.format(template, strings);
    }
}
