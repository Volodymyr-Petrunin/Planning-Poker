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
import planing.poker.common.ExceptionMessages;
import planing.poker.domain.Event;
import planing.poker.domain.dto.EventDto;
import planing.poker.mapper.EventMapper;
import planing.poker.repository.EventRepository;


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
@DisplayName("Event Service Tests")
class EventServiceTest {

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static EventDto expectedDto;

    private static Event expectedEntity;

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private ExceptionMessages exceptionMessages;

    @BeforeAll
    static void setUp() {
        expectedDto = fixtureMonkey.giveMeOne(EventDto.class);
        expectedEntity = fixtureMonkey.giveMeOne(Event.class);
    }

    @Test
    @DisplayName("Create Event: Should create event and return correct DTO")
    void testCreateEvent_ShouldCreateEvent_AndReturnCorrectDto() {
        when(eventMapper.toEntity(expectedDto)).thenReturn(expectedEntity);
        when(eventRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(eventMapper.toDto(expectedEntity)).thenReturn(expectedDto);

        final EventDto createdEvent = eventService.createEvent(expectedDto);

        assertNotNull(createdEvent);

        verify(eventRepository, times(1)).save(expectedEntity);
        verify(eventMapper, times(1)).toEntity(expectedDto);
        verify(eventMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get All Events: Should return all events as a list of DTOs")
    void testGetAllEvents_ShouldReturnAllEvents() {
        when(eventRepository.findAll()).thenReturn(List.of(expectedEntity));
        when(eventMapper.toDto(expectedEntity)).thenReturn(expectedDto);

        final List<EventDto> events = eventService.getAllEvents();

        assertNotNull(events);
        assertEquals(1, events.size());

        verify(eventRepository, times(1)).findAll();
        verify(eventMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Event By ID: Should return the correct event DTO for given ID")
    void testGetEventById_ShouldReturnEvent() {
        when(eventRepository.findById(expectedDto.getId())).thenReturn(Optional.of(expectedEntity));
        when(eventMapper.toDto(expectedEntity)).thenReturn(expectedDto);

        final EventDto event = eventService.getEventById(expectedDto.getId());

        assertNotNull(event);

        verify(eventRepository, times(1)).findById(expectedDto.getId());
        verify(eventMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Event By ID: Should throw exception if event is not found")
    void testGetEventById_ShouldThrowExceptionIfNotFound() {
        when(eventRepository.findById(expectedDto.getId())).thenReturn(Optional.empty());
        when(exceptionMessages.NO_FIND_MESSAGE()).thenReturn("Error Message!");

        assertThrows(IllegalArgumentException.class, () -> eventService.getEventById(expectedDto.getId()));

        verify(eventRepository, times(1)).findById(expectedDto.getId());
    }

    @Test
    @DisplayName("Update Event: Should update event and return the updated DTO")
    void testUpdateEvent_ShouldUpdateEvent_AndReturnUpdatedDto() {
        when(eventMapper.toEntity(expectedDto)).thenReturn(expectedEntity);
        when(eventRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(eventMapper.toDto(expectedEntity)).thenReturn(expectedDto);

        final EventDto updatedEvent = eventService.updateEvent(expectedDto);

        assertNotNull(updatedEvent);

        verify(eventRepository, times(1)).save(expectedEntity);
        verify(eventMapper, times(1)).toEntity(expectedDto);
        verify(eventMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Delete Event: Should delete event by given ID")
    void testDeleteEvent_ShouldDeleteEvent() {
        doNothing().when(eventRepository).deleteById(expectedDto.getId());

        eventService.deleteEvent(expectedDto.getId());

        verify(eventRepository, times(1)).deleteById(expectedDto.getId());
    }
}
