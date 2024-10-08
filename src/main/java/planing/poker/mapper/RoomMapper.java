package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.Room;
import planing.poker.domain.dto.RoomDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {
    Room toEntity(RoomDto roomDto);

    RoomDto toDto(Room room);
}