package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.EventMessage;
import planing.poker.domain.dto.response.ResponseEventMessageDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface EventMessageMapper {
    EventMessage toEntity(ResponseEventMessageDto responseEventMessageDto);

    ResponseEventMessageDto toDto(EventMessage eventMessage);
}