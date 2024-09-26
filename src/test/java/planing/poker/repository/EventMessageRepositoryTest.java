package planing.poker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.Role;
import planing.poker.domain.EventMessage;
import planing.poker.domain.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Event Message Repository Tests")
@Sql(value = "classpath:script/event_message_repository.sql")
@Transactional
class EventMessageRepositoryTest {

    private static final Long EXPECTED_ID = 1L;

    private static final String EXPECTED_MESSAGE = "Sample event message";

    private static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime.of(2024, 1, 1, 0, 0, 0);

    private static final User EXPECTED_USER = new User(EXPECTED_ID, "User Name", "User Lastname",
            "User Nickname", "user@email.com", "user_pass", Role.USER_SPECTATOR, null);

    private static final EventMessage EXPECTED_EVENT_MESSAGE = new EventMessage(EXPECTED_ID, EXPECTED_USER, EXPECTED_MESSAGE, FIXED_TIMESTAMP);

    @Autowired
    private EventMessageRepository eventMessageRepository;

    private EventMessage expected;
    private EventMessage actual;

    @Test
    @DisplayName("Create Event Message: Should create an event message and return it with a generated ID")
    void testCreateEventMessage_ShouldCreateExpectedEventMessage_AndReturnWithId() {
        expected = new EventMessage(null, EXPECTED_USER, EXPECTED_MESSAGE, FIXED_TIMESTAMP);

        actual = eventMessageRepository.save(expected);

        assertNotNull(actual.getId());
        assertEquals(expected.getUser(), actual.getUser());
        assertEquals(expected.getMessage(), actual.getMessage());
        assertEquals(expected.getTimestamp(), actual.getTimestamp());
    }

    @Test
    @DisplayName("Find Event Message by ID: Should find an event message by its ID and return the expected message")
    void testFindById_ShouldFindEventMessageById_AndReturnExpectedEventMessage() {
        final Long messageId = EXPECTED_ID;
        expected = new EventMessage(messageId, EXPECTED_USER, EXPECTED_MESSAGE, FIXED_TIMESTAMP);

        actual = eventMessageRepository.findById(messageId).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch event message with id: " + messageId));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Event Messages: Should return all event messages and match the expected list")
    void testFindAllEventMessages_ShouldReturnAllEventMessages_AndReturnExpectedList() {
        final List<EventMessage> actualMessages = eventMessageRepository.findAll();

        assertEquals(List.of(EXPECTED_EVENT_MESSAGE), actualMessages);
    }

    @Test
    @DisplayName("Delete Event Message by ID: Should delete an event message and return remaining messages")
    void testDeleteEventMessageById_ShouldDeleteEventMessageWithCorrectId_AndFindAllShouldReturnRemainingMessages() {
        eventMessageRepository.deleteById(EXPECTED_ID);

        final List<EventMessage> remainingMessages = eventMessageRepository.findAll();

        assertTrue(remainingMessages.isEmpty());
    }

    @Test
    @DisplayName("Insert Batch of Event Messages: Should insert a batch of messages and return the expected list")
    void testInsertBatchOfEventMessages_ShouldInsertBatchOfMessages_AndReturnExpectedList() {
        final User newUser = new User(null, "Another User", "Lastname", "Nickname",
                "another@email.com", "password", Role.USER_SPECTATOR, Collections.emptyList());

        final List<EventMessage> messageBatch = Arrays.asList(
                new EventMessage(null, EXPECTED_USER, "First event message", LocalDateTime.now()),
                new EventMessage(null, newUser, "Second event message", LocalDateTime.now())
        );

        final List<EventMessage> actualMessages = eventMessageRepository.saveAll(messageBatch);

        assertEquals(messageBatch.size(), actualMessages.size());
        assertEquals(messageBatch, actualMessages);
    }
}