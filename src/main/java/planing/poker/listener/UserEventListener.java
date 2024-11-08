package planing.poker.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import planing.poker.event.user.UserChangeRoleEvent;
import planing.poker.event.user.UserCreatedEvent;
import planing.poker.event.user.UserDeletedEvent;
import planing.poker.event.user.UserUpdatedEvent;

@Component
public class UserEventListener {

    private final SimpMessagingTemplate messagingTemplate;

    public static final String TOPIC_ROOM_CREATED = "/topic/userCreated";

    public static final String TOPIC_ROOM_UPDATED = "/topic/userUpdated";

    public static final String TOPIC_ROOM_DELETED = "/topic/userDeleted";

    private static final String TOPIC_CHANGE_USER_ROLE = "/topic/userRoleChanged";

    @Autowired
    public UserEventListener(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleUserCreated(final UserCreatedEvent event) {
        messagingTemplate.convertAndSend(TOPIC_ROOM_CREATED, event.getResponseUserDto());
    }

    @EventListener
    public void handleUserUpdated(final UserUpdatedEvent event) {
        messagingTemplate.convertAndSend(TOPIC_ROOM_UPDATED, event.getResponseUserDto());
    }

    @EventListener
    public void handleUserDeleted(final UserDeletedEvent event) {
        messagingTemplate.convertAndSend(TOPIC_ROOM_DELETED, event.getUserId());
    }

    @EventListener
    public void handelUserChangeRole(final UserChangeRoleEvent event) {
        messagingTemplate.convertAndSend(TOPIC_CHANGE_USER_ROLE, event.getResponseUserDto());
    }

}
