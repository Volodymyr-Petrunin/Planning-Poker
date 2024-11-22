package planing.poker.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import planing.poker.event.eventmessage.EventMessageCreatedEvent;

@Component
public class EventMessageListener {

    private final SimpMessagingTemplate messagingTemplate;

    public static final String TOPIC_NEW_EVENT_MESSAGE = "/topic/newEventMessage";

    @Autowired
    public EventMessageListener(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handelEventMessages(final EventMessageCreatedEvent event) {
        messagingTemplate.convertAndSend(TOPIC_NEW_EVENT_MESSAGE, event);
    }
}
