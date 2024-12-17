package planing.poker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.domain.*;
import planing.poker.factory.utils.ExpectedEntityUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static planing.poker.factory.RoomFactory.createNewRoom;

@SpringBootTest
@DisplayName("Room Repository Tests")
@Sql(scripts = {"classpath:script/init_expected_data.sql"})
@Transactional
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    private Room expected;

    private Room actual;

    @Test
    @DisplayName("Create Room: Should create a room and return it with a generated ID")
    void testCreateRoom_ShouldCreateExpectedRoom_AndReturnWithId() {
        expected = ExpectedEntityUtils.getRoom();

        actual = roomRepository.save(expected);

        assertNotNull(actual.getId());
        assertEquals(expected.getCreator(), actual.getCreator());
        assertEquals(expected.getInvitedUsers(), actual.getInvitedUsers());
        assertEquals(expected.getCurrentStory(), actual.getCurrentStory());
        assertEquals(expected.getStories(), actual.getStories());
    }

    @Test
    @DisplayName("Find Room by ID: Should find a room by its ID and return the expected room")
    void testFindById_ShouldFindRoomById_AndReturnExpectedRoom() {
        expected = ExpectedEntityUtils.getRoom();

        actual = roomRepository.findById(expected.getId()).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch room with id: " + expected.getId()));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Rooms: Should return all rooms and match expected list")
    void testFindAllRooms_ShouldReturnAllRooms_AndReturnExpectedList() {
        final List<Room> actualRooms = roomRepository.findAll();

        assertEquals(List.of(ExpectedEntityUtils.getRoom()), actualRooms);
    }

    @Test
    @DisplayName("Delete Room by ID: Should delete a room and return remaining rooms")
    void testDeleteRoomById_ShouldDeleteRoomWithCorrectId_AndFindAllShouldReturnRemainingRooms() {
        roomRepository.deleteById(ExpectedEntityUtils.getRoom().getId());

        final List<Room> remainingRooms = roomRepository.findAll();

        assertTrue(remainingRooms.isEmpty());
    }

    @Test
    @DisplayName("Insert Batch of Rooms: Should insert a batch of rooms and return the expected list")
    void testInsertBatchOfRooms_ShouldInsertBatchOfRooms_AndReturnExpectedList() {
        final Room newRoom = createNewRoom();

        final List<Room> roomBatch = List.of(newRoom);

        final List<Room> actualRooms = roomRepository.saveAll(roomBatch);

        assertEquals(roomBatch.size(), actualRooms.size());
        assertEquals(roomBatch, actualRooms);
    }
}
