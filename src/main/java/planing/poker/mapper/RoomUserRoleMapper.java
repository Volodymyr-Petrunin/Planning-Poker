package planing.poker.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mapping;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import planing.poker.common.Role;
import planing.poker.domain.Room;
import planing.poker.domain.RoomUserRole;
import planing.poker.domain.User;
import planing.poker.domain.dto.request.RequestRoomUserRoleDto;
import planing.poker.domain.dto.response.ResponseRoomUserRoleDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class RoomUserRoleMapper {

    @Autowired
    private EntityManager entityManager;

    public abstract RoomUserRole toEntity(RequestRoomUserRoleDto responseRoomUserRoleDto);

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "userId", source = "user.id")
    public abstract ResponseRoomUserRoleDto toDto(RoomUserRole roomUserRole);

    @AfterMapping
    public void fetchEntities(@MappingTarget final RoomUserRole entity, final RequestRoomUserRoleDto requestDto) {
        entity.setUser(entityManager.find(User.class, requestDto.getUserId()));
        entity.setRoom(entityManager.find(Room.class, requestDto.getRoomId()));
    }

    @AfterMapping
    public void setRole(@MappingTarget final RoomUserRole entity, final RequestRoomUserRoleDto requestDto) {
        entity.setRole(Role.valueOf(requestDto.getNewRole()));
    }

    public RoomUserRole responseToEntity(final ResponseRoomUserRoleDto responseDto) {
        final RoomUserRole entity = new RoomUserRole();
        entity.setId(responseDto.getId());
        entity.setUser(entityManager.find(User.class, responseDto.getUserId()));
        entity.setRoom(entityManager.find(Room.class, responseDto.getRoomId()));
        entity.setRole(responseDto.getRole());
        return entity;
    }
}
