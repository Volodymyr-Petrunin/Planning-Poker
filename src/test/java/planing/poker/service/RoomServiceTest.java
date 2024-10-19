package planing.poker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import planing.poker.domain.Room;
import planing.poker.domain.User;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.domain.dto.response.ResponseUserDto;
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
    private static final ResponseRoomDto EXPECTED_DTO = ExpectedEntityDtoUtils.getRoom();
    private static final Room EXPECTED_ENTITY = ExpectedEntityUtils.getRoom();
    private static final ResponseUserDto RESPONSE_USER_DTO = ExpectedEntityDtoUtils.getUserCreator();
    private static final User EXPECTED_USER = ExpectedEntityUtils.getUserCreator();

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private UserService userService;

    @Test
    @DisplayName("Create Room: Should create room and return correct DTO")
    void testCreateRoom_ShouldCreateRoom_AndReturnCorrectDto() {
        final RequestRoomDto requestRoomDto = new RequestRoomDto();
        when(roomMapper.toEntity(requestRoomDto)).thenReturn(EXPECTED_ENTITY);
        when(roomRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(roomMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);
        when(userService.getUserByEmail(EXPECTED_USER.getEmail())).thenReturn(RESPONSE_USER_DTO);

        final ResponseRoomDto createdRoom = roomService.createRoom(requestRoomDto, EXPECTED_USER.getEmail());

        assertNotNull(createdRoom);

        verify(roomRepository, times(1)).save(EXPECTED_ENTITY);
        verify(roomMapper, times(1)).toEntity(requestRoomDto);
        verify(roomMapper, times(1)).toDto(EXPECTED_ENTITY);
        verify(userService, times(1)).getUserByEmail(EXPECTED_USER.getEmail());
    }

    @Test
    @DisplayName("Get All Rooms: Should return all rooms as a list of DTOs")
    void testGetAllRooms_ShouldReturnAllRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(EXPECTED_ENTITY));
        when(roomMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final List<ResponseRoomDto> rooms = roomService.getAllRooms();

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

        final ResponseRoomDto room = roomService.getRoomById(EXPECTED_DTO.getId());

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
        final RequestRoomDto requestRoomDto = new RequestRoomDto();
        when(roomMapper.toEntity(requestRoomDto)).thenReturn(EXPECTED_ENTITY);
        when(roomRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(roomMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseRoomDto updatedRoom = roomService.updateRoom(EXPECTED_DTO.getId(),requestRoomDto);

        assertNotNull(updatedRoom);

        verify(roomRepository, times(1)).save(EXPECTED_ENTITY);
        verify(roomMapper, times(1)).toEntity(requestRoomDto);
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
