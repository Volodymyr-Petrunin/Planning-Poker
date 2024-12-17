package planing.poker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.domain.Team;
import planing.poker.factory.utils.ExpectedEntityUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static planing.poker.factory.utils.ExpectedEntityUtils.getTeam;

@SpringBootTest
@DisplayName("Team Repository Tests")
@Sql(value = "classpath:script/init_expected_data.sql")
@Transactional
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    private Team expected;

    private Team actual;

    @Test
    @DisplayName("Create Event: Should create an event and return it with a generated ID")
    void testCreateEvent_ShouldCreateExpectedEvent_AndReturnWithId() {
        expected = new Team(null, "New Team", ExpectedEntityUtils.getUserElector(), Collections.emptyList());

        actual = teamRepository.save(expected);

        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find Event by ID: Should find an event by its ID and return the expected event")
    void testFindById_ShouldFindEventById_AndReturnExpectedEvent() {
        expected = getTeam();

        actual = teamRepository.findById(expected.getId()).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch event with id: " + expected.getId()));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Events: Should return all events and match expected list")
    void testFindAllEvents_ShouldReturnAllEvents_AndReturnExpectedList() {
        assertEquals(List.of(getTeam()), teamRepository.findAll());
    }

    @Test
    @DisplayName("Delete Event by ID: Should delete an event and return remaining events")
    void testDeleteEventById_ShouldDeleteEventWithCorrectId_AndFindAllShouldReturnRemainingEvents() {
        teamRepository.deleteById(1L);

        final List<Team> all = teamRepository.findAll();

        assertTrue(all.isEmpty());
    }
}