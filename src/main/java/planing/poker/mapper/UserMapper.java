package planing.poker.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import planing.poker.domain.Room;
import planing.poker.domain.User;
import planing.poker.domain.dto.update.UpdateUserDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.domain.dto.request.RequestUserDto;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoomUserRoleMapper.class})
public interface UserMapper {

    User toEntity(RequestUserDto userDto);

    @Mapping(target = "roomsId", source = "rooms")
    ResponseUserDto toDto(User user);

    User responseToEntity(ResponseUserDto responseUserDto);

    default void updateEntityFromDto(final UpdateUserDto dto, final ResponseUserDto entity) {
        if (dto.getFirstName() != null) entity.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) entity.setLastName(dto.getLastName());
        if (dto.getNickname() != null) entity.setNickname(dto.getNickname());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
    }

    default List<Long> mapRoomsToIds(List<Room> rooms) {
        if (rooms == null) {
            return Collections.emptyList();
        }
        return rooms.stream()
                .map(Room::getId)
                .toList();
    }
}