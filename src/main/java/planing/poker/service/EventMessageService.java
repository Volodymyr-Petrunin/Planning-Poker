package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.domain.dto.EventMessageDto;
import planing.poker.mapper.EventMessageMapper;
import planing.poker.repository.EventMessageRepository;

import java.util.List;

@Service
public class EventMessageService {

    private final EventMessageRepository eventMessageRepository;

    private final EventMessageMapper eventMessageMapper;

    @Autowired
    public EventMessageService(EventMessageRepository eventMessageRepository,
                               EventMessageMapper eventMessageMapper) {
        this.eventMessageRepository = eventMessageRepository;
        this.eventMessageMapper = eventMessageMapper;
    }

    public EventMessageDto createEventMessage(final EventMessageDto eventMessageDto) {
        return eventMessageMapper.toDto(eventMessageRepository.save(eventMessageMapper.toEntity(eventMessageDto)));
    }

    public List<EventMessageDto> getAllEventMessages() {
        return eventMessageRepository.findAll().stream().map(eventMessageMapper::toDto).toList();
    }

    public EventMessageDto getEventMessageById(final Long id) {
        return eventMessageMapper.toDto(eventMessageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object")));
    }

    public EventMessageDto updateEventMessage(final EventMessageDto eventMessageDto) {
        return eventMessageMapper.toDto(eventMessageRepository.save(eventMessageMapper.toEntity(eventMessageDto)));
    }

    public void deleteEventMessage(final Long id) {
        eventMessageRepository.deleteById(id);
    }
}
