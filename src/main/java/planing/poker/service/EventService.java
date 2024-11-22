package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.common.ExceptionMessages;
import planing.poker.domain.Event;
import planing.poker.domain.EventMessage;
import planing.poker.domain.dto.EventDto;
import planing.poker.mapper.EventMapper;
import planing.poker.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final ExceptionMessages exceptionMessages;

    @Autowired
    public EventService(final EventRepository eventRepository, final EventMapper eventMapper, final ExceptionMessages exceptionMessages) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.exceptionMessages = exceptionMessages;
    }

    public Event createEvent() {
        return eventRepository.save(new Event(null, null, new ArrayList<>()));
    }

    public EventDto createEvent(final EventDto eventDto) {
        return eventMapper.toDto(eventRepository.save(eventMapper.toEntity(eventDto)));
    }

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream().map(eventMapper::toDto).toList();
    }

    public EventDto getEventById(final Long id) {
        return eventMapper.toDto(eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE())));
    }

    public EventDto updateEvent(final EventDto eventDto) {
        return eventMapper.toDto(eventRepository.save(eventMapper.toEntity(eventDto)));
    }

    public EventDto addMessageToEvent(final long eventId, final EventMessage eventMessage) {
        final Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE()));

        event.getEventMessages().add(eventMessage);

        return eventMapper.toDto(eventRepository.save(event));
    }

    public void deleteEvent(final Long id) {
        eventRepository.deleteById(id);
    }
}
