package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.User;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.domain.dto.request.RequestUserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toEntity(RequestUserDto userDto);

    ResponseUserDto toDto(User user);
}