package planing.poker.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import planing.poker.common.ExceptionMessages;
import planing.poker.common.exception.RoomNotFoundException;
import planing.poker.common.factory.EventMessageFactory;
import planing.poker.common.generation.RoomCodeGeneration;
import planing.poker.domain.Event;
import planing.poker.domain.Room;
import planing.poker.domain.Story;
import planing.poker.domain.User;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.event.room.RoomCreatedEvent;
import planing.poker.event.room.RoomDeletedEvent;
import planing.poker.event.room.RoomUpdatedEvent;
import planing.poker.mapper.RoomMapper;
import planing.poker.mapper.StoryMapper;
import planing.poker.repository.EventRepository;
import planing.poker.repository.RoomRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@DisplayName("Room Service Tests")
class RoomServiceTest {

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static ResponseRoomDto responseRoomDto;

    private static RequestRoomDto requestRoomDto;

    private static Room expectedEntity;

    private static ResponseUserDto responseUserDto;

    private static User expectedUser;

    private static Event expectedEvent;

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private StoryMapper storyMapper;

    @Mock
    private UserService userService;

    @Mock
    private StoryService storyService;

    @Mock
    private RoomUserRoleService roomUserRoleService;

    @Mock
    private EventMessageService eventMessageService;

    @Mock
    private EventService eventService;

    @Mock
    private ExceptionMessages exceptionMessages;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private RoomCodeGeneration roomCodeGeneration;

    @Mock
    private EventMessageFactory eventMessageFactory;

    @BeforeAll
    static void setUp() {
        responseRoomDto = fixtureMonkey.giveMeOne(ResponseRoomDto.class);
        requestRoomDto = fixtureMonkey.giveMeOne(RequestRoomDto.class);
        expectedEntity = fixtureMonkey.giveMeOne(Room.class);
        responseUserDto = fixtureMonkey.giveMeOne(ResponseUserDto.class);
        expectedUser = fixtureMonkey.giveMeOne(User.class);
        expectedEvent = expectedEntity.getEvent();
    }

    @Test
    @DisplayName("Create Room: Should create room and return correct DTO")
    void testCreateRoom_ShouldCreateRoom_AndReturnCorrectDto() {
        when(roomMapper.toEntity(requestRoomDto)).thenReturn(expectedEntity);
        when(roomCodeGeneration.generateCode()).thenReturn(expectedEntity.getRoomCode());
        when(storyMapper.responseDtoToEntity(any())).thenReturn(fixtureMonkey.giveMeOne(Story.class));
        when(eventService.createEvent()).thenReturn(expectedEvent);
        when(roomRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(roomMapper.toDto(expectedEntity)).thenReturn(responseRoomDto);
        when(userService.getUserByEmail(expectedUser.getEmail())).thenReturn(responseUserDto);

        final ResponseRoomDto createdRoom = roomService.createRoom(requestRoomDto, expectedUser.getEmail());

        assertNotNull(createdRoom);

        verify(roomRepository, times(1)).save(expectedEntity);
        verify(roomMapper, times(1)).toEntity(requestRoomDto);
        verify(roomMapper, times(1)).toDto(expectedEntity);
        verify(eventService, times(1)).createEvent();
        verify(userService, times(1)).getUserByEmail(expectedUser.getEmail());
        verify(applicationEventPublisher, times(1)).publishEvent(isA(RoomCreatedEvent.class));
    }

    @Test
    @DisplayName("Get All Rooms: Should return all rooms as a list of DTOs")
    void testGetAllRooms_ShouldReturnAllRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(expectedEntity));
        when(roomMapper.toDto(expectedEntity)).thenReturn(responseRoomDto);

        final List<ResponseRoomDto> rooms = roomService.getAllRooms();

        assertNotNull(rooms);
        assertEquals(1, rooms.size());

        verify(roomRepository, times(1)).findAll();
        verify(roomMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Room By ID: Should return the correct room DTO for given ID")
    void testGetRoomById_ShouldReturnRoom() {
        when(roomRepository.findById(responseRoomDto.getId())).thenReturn(Optional.of(expectedEntity));
        when(roomMapper.toDto(expectedEntity)).thenReturn(responseRoomDto);

        final ResponseRoomDto room = roomService.getRoomById(responseRoomDto.getId());

        assertNotNull(room);

        verify(roomRepository, times(1)).findById(responseRoomDto.getId());
        verify(roomMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Room By ID: Should throw exception if room is not found")
    void testGetRoomById_ShouldThrowExceptionIfNotFound() {
        when(roomRepository.findById(responseRoomDto.getId())).thenReturn(Optional.empty());
        when(exceptionMessages.NO_FIND_MESSAGE()).thenReturn("Error Message!");

        assertThrows(RoomNotFoundException.class, () -> roomService.getRoomById(responseRoomDto.getId()));

        verify(roomRepository, times(1)).findById(responseRoomDto.getId());
    }

    @Test
    @DisplayName("Update Room: Should update room and return the updated DTO")
    void testUpdateRoom_ShouldUpdateRoom_AndReturnUpdatedDto() {
        when(roomMapper.toEntity(requestRoomDto)).thenReturn(expectedEntity);
        when(roomRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(roomRepository.findById(responseRoomDto.getId())).thenReturn(Optional.of(expectedEntity));
        when(roomMapper.toDto(expectedEntity)).thenReturn(responseRoomDto);

        final ResponseRoomDto updatedRoom = roomService.updateRoom(responseRoomDto.getId(),requestRoomDto);

        assertNotNull(updatedRoom);

        verify(roomRepository, times(1)).save(expectedEntity);
        verify(roomMapper, times(1)).toEntity(requestRoomDto);
        verify(roomMapper, times(1)).toDto(expectedEntity);
        verify(applicationEventPublisher, times(1)).publishEvent(isA(RoomUpdatedEvent.class));
    }

    @Test
    @DisplayName("Delete Room: Should delete room by given ID")
    void testDeleteRoom_ShouldDeleteRoom() {
        when(roomRepository.findById(responseRoomDto.getId())).thenReturn(Optional.of(expectedEntity));
        doNothing().when(roomRepository).deleteById(responseRoomDto.getId());

        roomService.deleteRoom(responseRoomDto.getId());

        verify(roomRepository, times(1)).deleteById(responseRoomDto.getId());
        verify(applicationEventPublisher, times(1)).publishEvent(isA(RoomDeletedEvent.class));
    }
}
