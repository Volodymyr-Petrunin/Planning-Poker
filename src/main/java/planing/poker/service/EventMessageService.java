package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.ExceptionMessages;
import planing.poker.common.MessageUtils;
import planing.poker.domain.EventMessage;
import planing.poker.domain.dto.EventDto;
import planing.poker.domain.dto.request.RequestEventMessageDto;
import planing.poker.domain.dto.response.ResponseEventMessageDto;
import planing.poker.event.eventmessage.EventMessageCreatedEvent;
import planing.poker.mapper.EventMessageMapper;
import planing.poker.repository.EventMessageRepository;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class EventMessageService {

    private final EventMessageRepository eventMessageRepository;

    private final EventMessageMapper eventMessageMapper;

    private final MessageUtils messageUtils;

    private final ExceptionMessages exceptionMessages;

    private final EventService eventService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EventMessageService(final EventMessageRepository eventMessageRepository, final EventMessageMapper eventMessageMapper,
                               final MessageUtils messageUtils, final ExceptionMessages exceptionMessages,
                               final EventService eventService, final ApplicationEventPublisher applicationEventPublisher) {
        this.eventMessageRepository = eventMessageRepository;
        this.eventMessageMapper = eventMessageMapper;
        this.messageUtils = messageUtils;
        this.exceptionMessages = exceptionMessages;
        this.eventService = eventService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ResponseEventMessageDto createEventMessage(final RequestEventMessageDto requestEventMessageDto) {
        final EventMessage savedMessage = eventMessageRepository.save(eventMessageMapper.toEntity(requestEventMessageDto));

        final EventDto eventDto = eventService.addMessageToEvent(requestEventMessageDto.getEventId(), savedMessage);
        final ResponseEventMessageDto responseEventMessageDto = eventMessageMapper.toDto(savedMessage);

        applicationEventPublisher.publishEvent(new EventMessageCreatedEvent(eventDto.getId(), responseEventMessageDto));

        return responseEventMessageDto;
    }

    public List<ResponseEventMessageDto> getAllEventMessages() {
        return eventMessageRepository.findAll().stream().map(eventMessageMapper::toDto).toList();
    }

    public ResponseEventMessageDto getEventMessageById(final Long id) {
        return eventMessageMapper.toDto(eventMessageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE())));
    }

    public ResponseEventMessageDto updateEventMessage(final RequestEventMessageDto requestEventMessageDto) {
        return eventMessageMapper.toDto(eventMessageRepository.save(eventMessageMapper.toEntity(requestEventMessageDto)));
    }

    public void deleteEventMessage(final Long id) {
        eventMessageRepository.deleteById(id);
    }

    public String getLocalizedMessage(final ResponseEventMessageDto responseEventMessageDto, final Locale locale) {
        final EventMessage eventMessage = eventMessageMapper.responseToDto(responseEventMessageDto);
        final Object[] args = eventMessage.parseArgs();

        return messageUtils.getMessage(eventMessage.getMessageKey(), locale, args);
    }

}
