package planing.poker.factory;

import planing.poker.domain.EventMessage;
import planing.poker.domain.User;

import java.time.LocalDateTime;

public class EventMessageFactory {

    private static final Long EXPECTED_ID = 1L;
    private static final String EXPECTED_MESSAGE_ARGS = "The first story";
    private static final String EXPECTED_MESSAGE_KEY = "messages.event.CURRENT_STORY_SELECTED";
    private static final LocalDateTime EXPECTED_TIMESTAMP = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

    public static EventMessage createEventMessage(final User user) {
        return new EventMessage(EXPECTED_ID, user, EXPECTED_MESSAGE_KEY, EXPECTED_MESSAGE_ARGS, EXPECTED_TIMESTAMP);
    }
}
