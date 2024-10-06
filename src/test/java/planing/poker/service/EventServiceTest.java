package planing.poker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import planing.poker.domain.Event;
import planing.poker.domain.dto.EventDto;
import planing.poker.factory.utils.ExpectedEntityDtoUtils;
import planing.poker.factory.utils.ExpectedEntityUtils;
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
    private static final EventDto EXPECTED_DTO = ExpectedEntityDtoUtils.getEvent();
    private static final Event EXPECTED_ENTITY = ExpectedEntityUtils.getEvent();

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Test
    @DisplayName("Create Event: Should create event and return correct DTO")
    void testCreateEvent_ShouldCreateEvent_AndReturnCorrectDto() {
        when(eventMapper.toEntity(EXPECTED_DTO)).thenReturn(EXPECTED_ENTITY);
        when(eventRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(eventMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final EventDto createdEvent = eventService.createEvent(EXPECTED_DTO);

        assertNotNull(createdEvent);

        verify(eventRepository, times(1)).save(EXPECTED_ENTITY);
        verify(eventMapper, times(1)).toEntity(EXPECTED_DTO);
        verify(eventMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get All Events: Should return all events as a list of DTOs")
    void testGetAllEvents_ShouldReturnAllEvents() {
        when(eventRepository.findAll()).thenReturn(List.of(EXPECTED_ENTITY));
        when(eventMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final List<EventDto> events = eventService.getAllEvents();

        assertNotNull(events);
        assertEquals(1, events.size());

        verify(eventRepository, times(1)).findAll();
        verify(eventMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Event By ID: Should return the correct event DTO for given ID")
    void testGetEventById_ShouldReturnEvent() {
        when(eventRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.of(EXPECTED_ENTITY));
        when(eventMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final EventDto event = eventService.getEventById(EXPECTED_DTO.getId());

        assertNotNull(event);

        verify(eventRepository, times(1)).findById(EXPECTED_DTO.getId());
        verify(eventMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Event By ID: Should throw exception if event is not found")
    void testGetEventById_ShouldThrowExceptionIfNotFound() {
        when(eventRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.empty());

        final Exception exception = assertThrows(
                IllegalArgumentException.class, () -> eventService.getEventById(EXPECTED_DTO.getId()));

        assertEquals("message.not.find.object", exception.getMessage());

        verify(eventRepository, times(1)).findById(EXPECTED_DTO.getId());
    }

    @Test
    @DisplayName("Update Event: Should update event and return the updated DTO")
    void testUpdateEvent_ShouldUpdateEvent_AndReturnUpdatedDto() {
        when(eventMapper.toEntity(EXPECTED_DTO)).thenReturn(EXPECTED_ENTITY);
        when(eventRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(eventMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final EventDto updatedEvent = eventService.updateEvent(EXPECTED_DTO);

        assertNotNull(updatedEvent);

        verify(eventRepository, times(1)).save(EXPECTED_ENTITY);
        verify(eventMapper, times(1)).toEntity(EXPECTED_DTO);
        verify(eventMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Delete Event: Should delete event by given ID")
    void testDeleteEvent_ShouldDeleteEvent() {
        doNothing().when(eventRepository).deleteById(EXPECTED_DTO.getId());

        eventService.deleteEvent(EXPECTED_DTO.getId());

        verify(eventRepository, times(1)).deleteById(EXPECTED_DTO.getId());
    }
}
