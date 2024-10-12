package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.User;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.domain.dto.request.RequestUserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "nickname", source = "nickname")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    User toEntity(RequestUserDto userDto);

    ResponseUserDto toDto(User user);
}