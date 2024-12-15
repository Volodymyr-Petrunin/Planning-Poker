package planing.poker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import planing.poker.domain.Room;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.request.RequestRoomDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoomUserRoleMapper.class, EventMapper.class})
public interface RoomMapper {
    Room toEntity(RequestRoomDto requestRoomDto);

    ResponseRoomDto toDto(Room room);

    Room responseToEntity(ResponseRoomDto responseRoomDto);
}