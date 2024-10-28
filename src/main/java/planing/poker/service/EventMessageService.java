package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.common.Messages;
import planing.poker.domain.dto.EventMessageDto;
import planing.poker.mapper.EventMessageMapper;
import planing.poker.repository.EventMessageRepository;

import java.util.List;

@Service
public class EventMessageService {

    private final EventMessageRepository eventMessageRepository;

    private final EventMessageMapper eventMessageMapper;

    private final Messages messages;

    @Autowired
    public EventMessageService(final EventMessageRepository eventMessageRepository,
                               final EventMessageMapper eventMessageMapper, final Messages messages) {
        this.eventMessageRepository = eventMessageRepository;
        this.eventMessageMapper = eventMessageMapper;
        this.messages = messages;
    }

    public EventMessageDto createEventMessage(final EventMessageDto eventMessageDto) {
        return eventMessageMapper.toDto(eventMessageRepository.save(eventMessageMapper.toEntity(eventMessageDto)));
    }

    public List<EventMessageDto> getAllEventMessages() {
        return eventMessageRepository.findAll().stream().map(eventMessageMapper::toDto).toList();
    }

    public EventMessageDto getEventMessageById(final Long id) {
        return eventMessageMapper.toDto(eventMessageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(messages.NO_FIND_MESSAGE())));
    }

    public EventMessageDto updateEventMessage(final EventMessageDto eventMessageDto) {
        return eventMessageMapper.toDto(eventMessageRepository.save(eventMessageMapper.toEntity(eventMessageDto)));
    }

    public void deleteEventMessage(final Long id) {
        eventMessageRepository.deleteById(id);
    }
}
