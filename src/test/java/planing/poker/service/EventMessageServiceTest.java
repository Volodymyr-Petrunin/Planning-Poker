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
import org.springframework.context.annotation.Import;
import planing.poker.common.ErrorStatusFilter;
import planing.poker.common.ExceptionMessages;
import planing.poker.common.generation.RoomCodeGeneration;
import planing.poker.domain.EventMessage;
import planing.poker.domain.dto.EventDto;
import planing.poker.domain.dto.request.RequestEventMessageDto;
import planing.poker.domain.dto.response.ResponseEventMessageDto;
import planing.poker.mapper.EventMessageMapper;
import planing.poker.repository.EventMessageRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@DisplayName("Event Message Service Tests")
@Import({ErrorStatusFilter.class, RoomCodeGeneration.class})
class EventMessageServiceTest {

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static ResponseEventMessageDto expectedResponseDto;

    private static RequestEventMessageDto expectedRequestDto;

    private static EventMessage expectedEntity;

    private static EventDto expectedEventDto;

    @InjectMocks
    private EventMessageService eventMessageService;

    @Mock
    private EventMessageRepository eventMessageRepository;

    @Mock
    private EventMessageMapper eventMessageMapper;

    @Mock
    private EventService eventService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private ExceptionMessages exceptionMessages;

    @BeforeAll
    static void setUp() {
         expectedResponseDto = fixtureMonkey.giveMeOne(ResponseEventMessageDto.class);
         expectedRequestDto = fixtureMonkey.giveMeOne(RequestEventMessageDto.class);
         expectedEntity = fixtureMonkey.giveMeBuilder(EventMessage.class)
                 .set("id", expectedRequestDto.getEventId())
                 .set("messageArgs", expectedRequestDto.getMessageArgs())
                 .set("messageKey", expectedRequestDto.getMessageKey())
                 .set("timestamp", expectedRequestDto.getTimestamp())
                 .sample();
         expectedEventDto = fixtureMonkey.giveMeOne(EventDto.class);
    }


    @Test
    @DisplayName("Create Event Message: Should create message and return correct DTO")
    void testCreateEventMessage_ShouldCreateEventMessage_AndReturnCorrectDto() {
        when(eventMessageMapper.toEntity(expectedRequestDto)).thenReturn(expectedEntity);
        when(eventMessageRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(eventService.addMessageToEvent(expectedRequestDto.getEventId(), expectedEntity)).thenReturn(expectedEventDto);
        when(eventMessageMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseEventMessageDto createdMessage = eventMessageService.createEventMessage(expectedRequestDto);

        assertNotNull(createdMessage);

        verify(eventMessageRepository, times(1)).save(expectedEntity);
        verify(eventMessageMapper, times(1)).toEntity(expectedRequestDto);
        verify(eventMessageMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get All Event Messages: Should return all messages as a list of DTOs")
    void testGetAllEventMessages_ShouldReturnAllMessages() {
        when(eventMessageRepository.findAll()).thenReturn(List.of(expectedEntity));
        when(eventMessageMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final List<ResponseEventMessageDto> messages = eventMessageService.getAllEventMessages();

        assertNotNull(messages);

        verify(eventMessageRepository, times(1)).findAll();
        verify(eventMessageMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Event Message By ID: Should return the correct message DTO for given ID")
    void testGetEventMessageById_ShouldReturnMessage() {
        when(eventMessageRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.of(expectedEntity));
        when(eventMessageMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseEventMessageDto message = eventMessageService.getEventMessageById(expectedResponseDto.getId());

        assertNotNull(message);

        verify(eventMessageRepository, times(1)).findById(expectedResponseDto.getId());
        verify(eventMessageMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get Event Message By ID: Should throw exception if message is not found")
    void testGetEventMessageById_ShouldThrowExceptionIfNotFound() {
        when(eventMessageRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.empty());
        when(exceptionMessages.NO_FIND_MESSAGE()).thenReturn("Error Message!");

        assertThrows(IllegalArgumentException.class, () -> eventMessageService.getEventMessageById(expectedResponseDto.getId()));

        verify(eventMessageRepository, times(1)).findById(expectedResponseDto.getId());
    }

    @Test
    @DisplayName("Update Event Message: Should update message and return the updated DTO")
    void testUpdateEventMessage_ShouldUpdateMessage_AndReturnUpdatedDto() {
        when(eventMessageMapper.toEntity(expectedRequestDto)).thenReturn(expectedEntity);
        when(eventMessageRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(eventMessageMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseEventMessageDto updatedMessage = eventMessageService.updateEventMessage(expectedRequestDto);

        assertNotNull(updatedMessage);

        verify(eventMessageRepository, times(1)).save(expectedEntity);
        verify(eventMessageMapper, times(1)).toEntity(expectedRequestDto);
        verify(eventMessageMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Delete Event Message: Should delete message by given ID")
    void testDeleteEventMessage_ShouldDeleteMessage() {
        doNothing().when(eventMessageRepository).deleteById(expectedResponseDto.getId());

        eventMessageService.deleteEventMessage(expectedResponseDto.getId());

        verify(eventMessageRepository, times(1)).deleteById(expectedResponseDto.getId());
    }
}