package planing.poker.factory;

import planing.poker.domain.Event;
import planing.poker.domain.EventMessage;
import planing.poker.domain.Room;

import java.util.List;

public class EventFactory {

    private static final Long EXPECTED_ID = 1L;

    public static Event createEvent(final Room room, final List<EventMessage> eventMessages) {
        return new Event(EXPECTED_ID, room, eventMessages);
    }
}
