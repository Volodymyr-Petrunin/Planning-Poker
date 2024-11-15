package planing.poker.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import planing.poker.domain.Room;
import planing.poker.domain.User;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.domain.dto.request.RequestUserDto;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoomUserRoleMapper.class})
public interface UserMapper {

    User toEntity(final RequestUserDto userDto);

    @Mapping(target = "roomsId", source = "rooms")
    ResponseUserDto toDto(User user);

    User responseToEntity(final ResponseUserDto responseUserDto);

    default List<Long> mapRoomsToIds(List<Room> rooms) {
        if (rooms == null) {
            return Collections.emptyList();
        }
        return rooms.stream()
                .map(Room::getId)
                .toList();
    }
}