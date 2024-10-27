package planing.poker.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import planing.poker.event.room.RoomCreatedEvent;
import planing.poker.event.room.RoomCurrentStoryEvent;
import planing.poker.event.room.RoomDeletedEvent;
import planing.poker.event.room.RoomUpdatedEvent;

@Component
public class RoomEventListener {

    public static final String TOPIC_ROOM_CREATED = "/topic/roomCreated";

    public static final String TOPIC_ROOM_UPDATED = "/topic/roomUpdated";

    public static final String TOPIC_ROOM_DELETED = "/topic/roomDeleted";

    public static final String TOPIC_UPDATE_CURRENT_STORY = "/topic/updateCurrentStory";

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
    public void handleRoomCurrentStoryUpdate(final RoomCurrentStoryEvent event) {
        messagingTemplate.convertAndSend(TOPIC_UPDATE_CURRENT_STORY, event.getRoomDto());
    }

}
