package planing.poker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.Role;
import planing.poker.domain.EventMessage;
import planing.poker.domain.SecurityRole;
import planing.poker.domain.User;
import planing.poker.factory.utils.ExpectedEntityUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static planing.poker.factory.utils.ExpectedEntityUtils.getEventMessage;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Event Message Repository Tests")
@Sql(value = "classpath:script/init_expected_data.sql")
@Transactional
class EventMessageRepositoryTest {
    @Autowired
    private EventMessageRepository eventMessageRepository;

    private EventMessage expected;
    private EventMessage actual;

    @Test
    @DisplayName("Create Event Message: Should create an event message and return it with a generated ID")
    void testCreateEventMessage_ShouldCreateExpectedEventMessage_AndReturnWithId() {
        expected = new EventMessage(null, ExpectedEntityUtils.getUserCreator(), "Create room",
                LocalDateTime.of(2000, 1, 1, 1, 1, 1));

        actual = eventMessageRepository.save(expected);

        expected.setId(2L);

        assertNotNull(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Event Message by ID: Should find an event message by its ID and return the expected message")
    void testFindById_ShouldFindEventMessageById_AndReturnExpectedEventMessage() {
        expected = getEventMessage();

        actual = eventMessageRepository.findById(expected.getId()).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch event message with id: " + expected.getId()));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Event Messages: Should return all event messages and match the expected list")
    void testFindAllEventMessages_ShouldReturnAllEventMessages_AndReturnExpectedList() {
        final List<EventMessage> actualMessages = eventMessageRepository.findAll();

        assertEquals(List.of(getEventMessage()), actualMessages);
    }

    @Test
    @DisplayName("Delete Event Message by ID: Should delete an event message and return remaining messages")
    void testDeleteEventMessageById_ShouldDeleteEventMessageWithCorrectId_AndFindAllShouldReturnRemainingMessages() {
        eventMessageRepository.deleteById(getEventMessage().getId());

        final List<EventMessage> remainingMessages = eventMessageRepository.findAll();

        assertTrue(remainingMessages.isEmpty());
    }

    @Test
    @DisplayName("Insert Batch of Event Messages: Should insert a batch of messages and return the expected list")
    void testInsertBatchOfEventMessages_ShouldInsertBatchOfMessages_AndReturnExpectedList() {
        final User newUser = new User(null, "Another User", "Lastname", "Nickname",
                "another@email.com", "password", Role.USER_SPECTATOR, SecurityRole.ROLE_USER, Collections.emptyList());

        final List<EventMessage> messageBatch = Arrays.asList(
                new EventMessage(null, ExpectedEntityUtils.getUserElector(), "First event message", LocalDateTime.now()),
                new EventMessage(null, newUser, "Second event message", LocalDateTime.now())
        );

        final List<EventMessage> actualMessages = eventMessageRepository.saveAll(messageBatch);

        assertEquals(messageBatch.size(), actualMessages.size());
        assertEquals(messageBatch, actualMessages);
    }
}