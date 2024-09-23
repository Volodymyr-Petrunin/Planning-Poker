package planing.poker.mapper;

import org.mapstruct.*;
import planing.poker.domain.User;
import planing.poker.domain.dto.UserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoomMapper.class, StoryMapper.class, VoteMapper.class})
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
}