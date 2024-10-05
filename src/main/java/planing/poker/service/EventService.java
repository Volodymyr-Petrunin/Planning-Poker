package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.domain.dto.EventDto;
import planing.poker.mapper.EventMapper;
import planing.poker.repository.EventRepository;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventRepository eventRepository,
                        EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    public EventDto createEvent(final EventDto eventDto) {
        return eventMapper.toDto(eventRepository.save(eventMapper.toEntity(eventDto)));
    }

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream().map(eventMapper::toDto).toList();
    }

    public EventDto getEventById(final Long id) {
        return eventMapper.toDto(eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object")));
    }

    public EventDto updateEvent(final EventDto eventDto) {
        return eventMapper.toDto(eventRepository.save(eventMapper.toEntity(eventDto)));
    }

    public void deleteEvent(final Long id) {
        eventRepository.deleteById(id);
    }
}
