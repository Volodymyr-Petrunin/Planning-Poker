package planing.poker.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import planing.poker.domain.EventMessage;
import planing.poker.domain.User;
import planing.poker.domain.dto.request.RequestEventMessageDto;
import planing.poker.domain.dto.response.ResponseEventMessageDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public abstract class EventMessageMapper {

    @Autowired
    private EntityManager entityManager;

    public abstract EventMessage toEntity(RequestEventMessageDto requestEventMessageDto);

    public abstract ResponseEventMessageDto toDto(EventMessage eventMessage);

    public abstract EventMessage responseToDto(ResponseEventMessageDto responseEventMessageDto);

    @AfterMapping
    public void afterMapping(@MappingTarget final EventMessage entity, final RequestEventMessageDto requestDto) {
        entity.setUser(entityManager.find(User.class, requestDto.getUserId()));
    }
}