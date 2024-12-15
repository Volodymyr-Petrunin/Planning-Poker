package planing.poker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mapping;
import planing.poker.domain.Event;
import planing.poker.domain.dto.EventDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { EventMessageMapper.class}
)
public interface EventMapper {

    @Mapping(source = "roomId", target = "room.id")
    Event toEntity(EventDto eventDto);

    @Mapping(source = "room.id", target = "roomId")
    EventDto toDto(Event event);
}