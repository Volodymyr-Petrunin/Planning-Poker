package planing.poker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import planing.poker.domain.Room;
import planing.poker.domain.dto.RoomDto;
import planing.poker.factory.utils.ExpectedEntityDtoUtils;
import planing.poker.factory.utils.ExpectedEntityUtils;
import planing.poker.mapper.RoomMapper;
import planing.poker.repository.RoomRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@DisplayName("Room Service Tests")
class RoomServiceTest {
    private static final RoomDto EXPECTED_DTO = ExpectedEntityDtoUtils.getRoom();
    private static final Room EXPECTED_ENTITY = ExpectedEntityUtils.getRoom();

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomMapper roomMapper;

    @Test
    @DisplayName("Create Room: Should create room and return correct DTO")
    void testCreateRoom_ShouldCreateRoom_AndReturnCorrectDto() {
        when(roomMapper.toEntity(EXPECTED_DTO)).thenReturn(EXPECTED_ENTITY);
        when(roomRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(roomMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final RoomDto createdRoom = roomService.createRoom(EXPECTED_DTO);

        assertNotNull(createdRoom);

        verify(roomRepository, times(1)).save(EXPECTED_ENTITY);
        verify(roomMapper, times(1)).toEntity(EXPECTED_DTO);
        verify(roomMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get All Rooms: Should return all rooms as a list of DTOs")
    void testGetAllRooms_ShouldReturnAllRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(EXPECTED_ENTITY));
        when(roomMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final List<RoomDto> rooms = roomService.getAllRooms();

        assertNotNull(rooms);
        assertEquals(1, rooms.size());

        verify(roomRepository, times(1)).findAll();
        verify(roomMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Room By ID: Should return the correct room DTO for given ID")
    void testGetRoomById_ShouldReturnRoom() {
        when(roomRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.of(EXPECTED_ENTITY));
        when(roomMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final RoomDto room = roomService.getRoomById(EXPECTED_DTO.getId());

        assertNotNull(room);

        verify(roomRepository, times(1)).findById(EXPECTED_DTO.getId());
        verify(roomMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Room By ID: Should throw exception if room is not found")
    void testGetRoomById_ShouldThrowExceptionIfNotFound() {
        when(roomRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.empty());

        final Exception exception = assertThrows(
                IllegalArgumentException.class, () -> roomService.getRoomById(EXPECTED_DTO.getId()));

        assertEquals("message.not.find.object", exception.getMessage());

        verify(roomRepository, times(1)).findById(EXPECTED_DTO.getId());
    }

    @Test
    @DisplayName("Update Room: Should update room and return the updated DTO")
    void testUpdateRoom_ShouldUpdateRoom_AndReturnUpdatedDto() {
        when(roomMapper.toEntity(EXPECTED_DTO)).thenReturn(EXPECTED_ENTITY);
        when(roomRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(roomMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final RoomDto updatedRoom = roomService.updateRoom(EXPECTED_DTO);

        assertNotNull(updatedRoom);

        verify(roomRepository, times(1)).save(EXPECTED_ENTITY);
        verify(roomMapper, times(1)).toEntity(EXPECTED_DTO);
        verify(roomMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Delete Room: Should delete room by given ID")
    void testDeleteRoom_ShouldDeleteRoom() {
        doNothing().when(roomRepository).deleteById(EXPECTED_DTO.getId());

        roomService.deleteRoom(EXPECTED_DTO.getId());

        verify(roomRepository, times(1)).deleteById(EXPECTED_DTO.getId());
    }
}
