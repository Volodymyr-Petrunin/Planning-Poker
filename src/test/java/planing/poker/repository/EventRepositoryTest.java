package planing.poker.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.Role;
import planing.poker.domain.Event;
import planing.poker.domain.EventMessage;
import planing.poker.domain.Room;
import planing.poker.domain.User;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Event Repository Tests")
@Sql(value = "classpath:script/event_repository.sql")
@Transactional
class EventRepositoryTest {

    private static final Long EXPECTED_ID = 1L;

    private static final String EXPECTED_ROOM_NAME = "Test Room";

    private static final String EXPECTED_ROOM_CODE = "Test Room Code";

    private static final Date FIXED_DATE = Date.valueOf("2024-01-01");

    private static final LocalTime FIXED_TIME = LocalTime.of(12, 0);

    private static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

    private static final LocalTime VOTE_DURATION = LocalTime.of(1, 0);

    private static final Room EXPECTED_ROOM = createRoom(EXPECTED_ID, EXPECTED_ROOM_CODE, EXPECTED_ROOM_NAME,
            FIXED_DATE, FIXED_TIME, VOTE_DURATION, true, true);

    private static final User EXPECTED_USER = new User(EXPECTED_ID, "User Name", "User Lastname",
            "User Nickname", "user@email.com", "user_pass", Role.USER_SPECTATOR, null);

    private static final EventMessage EXPECTED_EVENT_MESSAGE = new EventMessage(EXPECTED_ID, EXPECTED_USER,
            "Sample event message", FIXED_TIMESTAMP);

    private static final Event EXPECTED_EVENT = new Event(EXPECTED_ID, EXPECTED_ROOM, List.of(EXPECTED_EVENT_MESSAGE));

    @Autowired
    private EventRepository eventRepository;

    private Event expected;
    private Event actual;

    @BeforeAll
    static void setUp() {
        EXPECTED_ROOM.setEvent(EXPECTED_EVENT);
    }

    @Test
    @DisplayName("Create Event: Should create an event and return it with a generated ID")
    void testCreateEvent_ShouldCreateExpectedEvent_AndReturnWithId() {
        expected = new Event(null, EXPECTED_ROOM, List.of(EXPECTED_EVENT_MESSAGE));

        actual = eventRepository.save(expected);

        assertNotNull(actual.getId());
        assertEquals(expected.getRoom(), actual.getRoom());
        assertEquals(expected.getEventMessages(), actual.getEventMessages());
    }

    @Test
    @DisplayName("Find Event by ID: Should find an event by its ID and return the expected event")
    void testFindById_ShouldFindEventById_AndReturnExpectedEvent() {
        final Long eventId = EXPECTED_ID;
        expected = new Event(eventId, EXPECTED_ROOM, List.of(EXPECTED_EVENT_MESSAGE));

        actual = eventRepository.findById(eventId).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch event with id: " + eventId));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Events: Should return all events and match expected list")
    void testFindAllEvents_ShouldReturnAllEvents_AndReturnExpectedList() {
        assertEquals(List.of(EXPECTED_EVENT), eventRepository.findAll());
    }

    @Test
    @DisplayName("Delete Event by ID: Should delete an event and return remaining events")
    void testDeleteEventById_ShouldDeleteEventWithCorrectId_AndFindAllShouldReturnRemainingEvents() {
        eventRepository.deleteById(EXPECTED_ID);

        final List<Event> remainingEvents = eventRepository.findAll();

        assertTrue(remainingEvents.isEmpty());
    }

    @Test
    @DisplayName("Insert Batch of Events: Should insert a batch of events and return the expected list")
    void testInsertBatchOfEvents_ShouldInsertBatchOfEvents_AndReturnExpectedList() {
        final Room newRoom = new Room();

        final List<EventMessage> newEventMessages = List.of (
                new EventMessage(null, EXPECTED_USER, "New message 1", FIXED_TIMESTAMP),
                new EventMessage(null, EXPECTED_USER, "New message 2", FIXED_TIMESTAMP)
        );

        final List<Event> eventBatch = List.of(
                new Event(null, EXPECTED_ROOM, List.of(EXPECTED_EVENT_MESSAGE)),
                new Event(null, newRoom, newEventMessages)
        );

        final List<Event> actualEvents = eventRepository.saveAll(eventBatch);

        newRoom.setId(2L);

        assertEquals(eventBatch.size(), actualEvents.size());
        assertEquals(eventBatch, actualEvents);
    }

    private static Room createRoom(final Long id, final String roomCode, final String roomName,
                            final Date startDate, final LocalTime startTime, final LocalTime voteDuration,
                            final Boolean isActive, final Boolean isVotingOpen) {
        return new Room()
                .setId(id)
                .setRoomCode(roomCode)
                .setRoomName(roomName)
                .setInvitedUsers(Collections.emptyList())
                .setStartDate(startDate)
                .setStartTime(startTime)
                .setStories(Collections.emptyList())
                .setVoteDuration(voteDuration)
                .setIsActive(isActive)
                .setIsVotingOpen(isVotingOpen);
    }
}