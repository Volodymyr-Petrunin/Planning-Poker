package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.Event;
import planing.poker.domain.dto.EventDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {EventMessageMapper.class})
public interface EventMapper {
    Event toEntity(EventDto eventDto);

    EventDto toDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event partialUpdate(EventDto eventDto, @MappingTarget Event event);
}