package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.EventMessage;
import planing.poker.domain.dto.EventMessageDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface EventMessageMapper {
    EventMessage toEntity(EventMessageDto eventMessageDto);

    EventMessageDto toDto(EventMessage eventMessage);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EventMessage partialUpdate(EventMessageDto eventMessageDto, @MappingTarget EventMessage eventMessage);
}