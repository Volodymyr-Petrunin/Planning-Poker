package planing.poker.listener;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import planing.poker.event.vote.VoteCreatedEvent;

@Component
public class VoteEventListener {

    private static final String TOPIC_NEW_VOTE_TEMPLATE = "/topic/newVote/%s";

    private final SimpMessagingTemplate messagingTemplate;

    public VoteEventListener(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleVoteCreatedEvent(final VoteCreatedEvent event) {
        final String topic = String.format(TOPIC_NEW_VOTE_TEMPLATE, event.getRoomCode());
        messagingTemplate.convertAndSend(topic, event.getResponseVoteDto());
    }
}
