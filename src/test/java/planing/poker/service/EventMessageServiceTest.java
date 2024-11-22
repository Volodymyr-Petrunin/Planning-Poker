package planing.poker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import planing.poker.domain.EventMessage;
import planing.poker.domain.dto.response.ResponseEventMessageDto;
import planing.poker.factory.utils.ExpectedEntityDtoUtils;
import planing.poker.factory.utils.ExpectedEntityUtils;
import planing.poker.mapper.EventMessageMapper;
import planing.poker.repository.EventMessageRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@DisplayName("Event Message Service Tests")
class EventMessageServiceTest {

    private static final ResponseEventMessageDto EXPECTED_DTO = ExpectedEntityDtoUtils.getEventMessage();

    private static final EventMessage EXPECTED_ENTITY = ExpectedEntityUtils.getEventMessage();

    @InjectMocks
    private EventMessageService eventMessageService;

    @Mock
    private EventMessageRepository eventMessageRepository;

    @Mock
    private EventMessageMapper eventMessageMapper;


    @Test
    @DisplayName("Create Event Message: Should create message and return correct DTO")
    void testCreateEventMessage_ShouldCreateEventMessage_AndReturnCorrectDto() {
        when(eventMessageMapper.toEntity(any())).thenReturn(EXPECTED_ENTITY);
        when(eventMessageRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(eventMessageMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseEventMessageDto createdMessage = eventMessageService.createEventMessage(any());

        assertNotNull(createdMessage);

        verify(eventMessageRepository, times(1)).save(EXPECTED_ENTITY);
        verify(eventMessageMapper, times(1)).toEntity(any());
        verify(eventMessageMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get All Event Messages: Should return all messages as a list of DTOs")
    void testGetAllEventMessages_ShouldReturnAllMessages() {
        when(eventMessageRepository.findAll()).thenReturn(List.of(EXPECTED_ENTITY));
        when(eventMessageMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final List<ResponseEventMessageDto> messages = eventMessageService.getAllEventMessages();

        assertNotNull(messages);

        verify(eventMessageRepository, times(1)).findAll();
        verify(eventMessageMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Event Message By ID: Should return the correct message DTO for given ID")
    void testGetEventMessageById_ShouldReturnMessage() {
        when(eventMessageRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.of(EXPECTED_ENTITY));
        when(eventMessageMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseEventMessageDto message = eventMessageService.getEventMessageById(EXPECTED_DTO.getId());

        assertNotNull(message);

        verify(eventMessageRepository, times(1)).findById(EXPECTED_DTO.getId());
        verify(eventMessageMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get Event Message By ID: Should throw exception if message is not found")
    void testGetEventMessageById_ShouldThrowExceptionIfNotFound() {
        when(eventMessageRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.empty());

        final Exception exception = assertThrows(
                IllegalArgumentException.class, () -> eventMessageService.getEventMessageById(EXPECTED_DTO.getId()));

        assertEquals("message.not.find.object", exception.getMessage());

        verify(eventMessageRepository, times(1)).findById(EXPECTED_DTO.getId());
    }

    @Test
    @DisplayName("Update Event Message: Should update message and return the updated DTO")
    void testUpdateEventMessage_ShouldUpdateMessage_AndReturnUpdatedDto() {
        when(eventMessageMapper.toEntity(any())).thenReturn(EXPECTED_ENTITY);
        when(eventMessageRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(eventMessageMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final ResponseEventMessageDto updatedMessage = eventMessageService.updateEventMessage(any());

        assertNotNull(updatedMessage);

        verify(eventMessageRepository, times(1)).save(EXPECTED_ENTITY);
        verify(eventMessageMapper, times(1)).toEntity(any());
        verify(eventMessageMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Delete Event Message: Should delete message by given ID")
    void testDeleteEventMessage_ShouldDeleteMessage() {
        doNothing().when(eventMessageRepository).deleteById(EXPECTED_DTO.getId());

        eventMessageService.deleteEventMessage(EXPECTED_DTO.getId());

        verify(eventMessageRepository, times(1)).deleteById(EXPECTED_DTO.getId());
    }
}