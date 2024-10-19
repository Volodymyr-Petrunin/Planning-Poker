package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.User;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.domain.dto.request.RequestUserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toEntity(final RequestUserDto userDto);

    /** TODO could be bag here because after mapping we can't get rooms but data will be in db*/
    @Mapping(target = "rooms", ignore = true)
    ResponseUserDto toDto(final User user);
}