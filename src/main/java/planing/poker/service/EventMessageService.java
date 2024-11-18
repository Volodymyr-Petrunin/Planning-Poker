package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.common.ExceptionMessages;
import planing.poker.common.MessageUtils;
import planing.poker.domain.EventMessage;
import planing.poker.domain.dto.EventMessageDto;
import planing.poker.mapper.EventMessageMapper;
import planing.poker.repository.EventMessageRepository;

import java.util.List;
import java.util.Locale;

@Service
public class EventMessageService {

    private final EventMessageRepository eventMessageRepository;

    private final EventMessageMapper eventMessageMapper;

    private final MessageUtils messageUtils;

    private final ExceptionMessages exceptionMessages;

    @Autowired
    public EventMessageService(final EventMessageRepository eventMessageRepository, final EventMessageMapper eventMessageMapper,
                               final MessageUtils messageUtils, final ExceptionMessages exceptionMessages) {
        this.eventMessageRepository = eventMessageRepository;
        this.eventMessageMapper = eventMessageMapper;
        this.messageUtils = messageUtils;
        this.exceptionMessages = exceptionMessages;
    }

    public EventMessageDto createEventMessage(final EventMessageDto eventMessageDto) {
        return eventMessageMapper.toDto(eventMessageRepository.save(eventMessageMapper.toEntity(eventMessageDto)));
    }

    public List<EventMessageDto> getAllEventMessages() {
        return eventMessageRepository.findAll().stream().map(eventMessageMapper::toDto).toList();
    }

    public EventMessageDto getEventMessageById(final Long id) {
        return eventMessageMapper.toDto(eventMessageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE())));
    }

    public EventMessageDto updateEventMessage(final EventMessageDto eventMessageDto) {
        return eventMessageMapper.toDto(eventMessageRepository.save(eventMessageMapper.toEntity(eventMessageDto)));
    }

    public void deleteEventMessage(final Long id) {
        eventMessageRepository.deleteById(id);
    }

    public String getLocalizedMessage(final EventMessage eventMessage, final Locale locale) {
        final Object[] args = eventMessage.parseArgs();
        return messageUtils.getMessage(eventMessage.getMessageKey(), locale, args);
    }

}
