package planing.poker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.domain.Event;
import planing.poker.domain.EventMessage;
import planing.poker.domain.Room;
import planing.poker.factory.utils.ExpectedEntityUtils;

import java.time.LocalDateTime;
import java.util.List;

import static planing.poker.factory.utils.ExpectedEntityUtils.getEvent;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Event Repository Tests")
@Sql(value = "classpath:script/init_expected_data.sql")
@Transactional
class EventRepositoryTest {
    private static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime
            .of(2024, 1, 1, 0, 0, 0);

    @Autowired
    private EventRepository eventRepository;

    private Event expected;
    private Event actual;


    @Test
    @DisplayName("Create Event: Should create an event and return it with a generated ID")
    void testCreateEvent_ShouldCreateExpectedEvent_AndReturnWithId() {
        expected = new Event(null, ExpectedEntityUtils.getRoom(), List.of(ExpectedEntityUtils.getEventMessage()));

        actual = eventRepository.save(expected);

        assertNotNull(actual.getId());
        assertEquals(expected.getRoom(), actual.getRoom());
        assertEquals(expected.getEventMessages(), actual.getEventMessages());
    }

    @Test
    @DisplayName("Find Event by ID: Should find an event by its ID and return the expected event")
    void testFindById_ShouldFindEventById_AndReturnExpectedEvent() {
        expected = getEvent();

        actual = eventRepository.findById(expected.getId()).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch event with id: " + expected.getId()));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Events: Should return all events and match expected list")
    void testFindAllEvents_ShouldReturnAllEvents_AndReturnExpectedList() {
        assertEquals(List.of(getEvent()), eventRepository.findAll());
    }

    @Test
    @DisplayName("Delete Event by ID: Should delete an event and return remaining events")
    void testDeleteEventById_ShouldDeleteEventWithCorrectId_AndFindAllShouldReturnRemainingEvents() {
        eventRepository.deleteById(1L);

        final List<Event> remainingEvents = eventRepository.findAll();

        assertTrue(remainingEvents.isEmpty());
    }

    @Test
    @DisplayName("Insert Batch of Events: Should insert a batch of events and return the expected list")
    void testInsertBatchOfEvents_ShouldInsertBatchOfEvents_AndReturnExpectedList() {
        final Room newRoom = new Room();

        final List<EventMessage> newEventMessages = List.of (
                new EventMessage(null, ExpectedEntityUtils.getUserCreator(), "any.key", "arg", FIXED_TIMESTAMP),
                new EventMessage(null, ExpectedEntityUtils.getUserElector(), "any.key", "arg", FIXED_TIMESTAMP)
        );

        final List<Event> eventBatch = List.of(
                new Event(null, ExpectedEntityUtils.getRoom(), List.of(ExpectedEntityUtils.getEventMessage())),
                new Event(null, newRoom, newEventMessages)
        );

        final List<Event> actualEvents = eventRepository.saveAll(eventBatch);

        newRoom.setId(2L);

        assertEquals(eventBatch.size(), actualEvents.size());
        assertEquals(eventBatch, actualEvents);
    }
}