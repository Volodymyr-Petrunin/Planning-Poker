package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.Room;
import planing.poker.domain.dto.RoomDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, StoryMapper.class})
public interface RoomMapper {
    Room toEntity(RoomDto roomDto);

    RoomDto toDto(Room room);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Room partialUpdate(RoomDto roomDto, @MappingTarget Room room);
}