package planing.poker.factory.utils;

import planing.poker.domain.*;
import planing.poker.factory.*;

import java.time.LocalDateTime;
import java.util.List;

public class ExpectedEntityUtils {
    private static final User userElector = UserFactory.createExpectedElector();
    private static final User userCreator = UserFactory.createUserCreator();
    private static final Team team = TeamFactory.createTeam(userCreator, List.of(userElector));
    private static final Story story = StoryFactory.createStory();
    private static final Room room = RoomFactory.createRoom(userCreator, story);
    private static final Vote vote = VoteFactory.createVote(userElector, story);
    private static final EventMessage eventMessage = EventMessageFactory.createEventMessage(userElector);
    private static final Event event = EventFactory.createEvent(room, List.of(eventMessage));
    private static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime
            .of(2024, 1, 1, 0, 0, 0);

    public static User getUserElector() {
        return userElector;
    }

    public static User getUserCreator() {
        return userCreator;
    }

    public static Team getTeam() {
        return team;
    }

    public static Story getStory() {
        return story;
    }

    public static Room getRoom() {
        return room;
    }

    public static Vote getVote() {
        return vote;
    }

    public static EventMessage getEventMessage() {
        return eventMessage;
    }

    public static Event getEvent() {
        return event;
    }

    public static LocalDateTime getFixedTimestamp() {
        return FIXED_TIMESTAMP;
    }
}
