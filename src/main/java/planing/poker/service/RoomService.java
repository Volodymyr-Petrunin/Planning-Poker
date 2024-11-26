package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.ExceptionMessages;
import planing.poker.common.Role;
import planing.poker.common.factory.EventMessageFactory;
import planing.poker.common.generation.RoomCodeGeneration;
import planing.poker.domain.*;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.event.room.*;
import planing.poker.mapper.RoomMapper;
import planing.poker.mapper.StoryMapper;
import planing.poker.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    private final StoryMapper storyMapper;

    private final RoomCodeGeneration roomCodeGeneration;

    private final UserService userService;

    private final StoryService storyService;

    private final RoomUserRoleService userRoleService;

    private final EventService eventService;

    private final EventMessageService eventMessageService;

    private final EventMessageFactory eventMessageFactory;

    private final ExceptionMessages exceptionMessages;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public RoomService(final RoomRepository roomRepository, final RoomMapper roomMapper,
                       final RoomCodeGeneration roomCodeGeneration, final UserService userService,
                       @Lazy final StoryService storyService, final StoryMapper storyMapper,
                       final RoomUserRoleService userRoleService, final EventService eventService,
                       final EventMessageService eventMessageService, final EventMessageFactory eventMessageFactory,
                       final ExceptionMessages exceptionMessages,
                       final ApplicationEventPublisher applicationEventPublisher) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.roomCodeGeneration = roomCodeGeneration;
        this.userService = userService;
        this.storyService = storyService;
        this.storyMapper = storyMapper;
        this.userRoleService = userRoleService;
        this.eventService = eventService;
        this.eventMessageService = eventMessageService;
        this.eventMessageFactory = eventMessageFactory;
        this.exceptionMessages = exceptionMessages;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ResponseRoomDto createRoom(final RequestRoomDto roomDto, final String userEmail) {
        setCreator(roomDto, userEmail);
        final List<ResponseStoryDto> stories = storyService.createSeveralStory(roomDto.getStories(), null);

        final Room room = roomMapper.toEntity(roomDto);
        setRoomCode(room);
        setStories(stories, room);
        room.setIsActive(true);
        room.setIsVotingOpen(false);
        room.setEvent(eventService.createEvent());
        updateEvent(room);

        final Room savedRoom = roomRepository.save(room);
        setRoomSpectatorRoleForInvitedUsers(savedRoom);

        final ResponseRoomDto savedRoomDto = roomMapper.toDto(savedRoom);
        applicationEventPublisher.publishEvent(new RoomCreatedEvent(savedRoomDto));

        return savedRoomDto;
    }

    public List<ResponseRoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(roomMapper::toDto).toList();
    }

    public List<ResponseRoomDto> getRoomsRelatedToUser(final User user) {
        if (user == null) {
            return Collections.emptyList();
        }

       return roomRepository.findRoomsRelatedToUser(user).stream().map(roomMapper::toDto).toList();
    }

    public ResponseRoomDto getRoomById(final Long id) {
        return roomRepository.findById(id).map(roomMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE()));
    }

    public ResponseRoomDto getRoomByCode(final String roomCode) {
        return roomMapper.toDto(roomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE())));
    }

    public ResponseRoomDto updateRoom(long id, final RequestRoomDto requestRoomDto) {
        if (roomRepository.findById(id).isPresent()) {
            final Room room = roomMapper.toEntity(requestRoomDto);
            room.setId(id);

            final ResponseRoomDto updatedRoom = roomMapper.toDto(roomRepository.save(room));
            applicationEventPublisher.publishEvent(new RoomUpdatedEvent(updatedRoom));

            return updatedRoom;
        } else {
            throw new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE());
        }
    }

    public ResponseRoomDto updateCurrentStory(final long roomId, final ResponseStoryDto responseStoryDto) {
        final ResponseRoomDto room = getRoomById(roomId);
        room.setCurrentStory(responseStoryDto);

        final ResponseRoomDto updatedRoom = roomMapper.toDto(roomRepository.save(roomMapper.responseToEntity(room)));
        applicationEventPublisher.publishEvent(new RoomCurrentStoryEvent(updatedRoom));

        eventMessageService.createEventMessage(
                eventMessageFactory.createMessageCurrentStorySelected(
                        updatedRoom.getEvent().getId(),
                        updatedRoom.getCreator().getId(),
                        updatedRoom.getCurrentStory().getTitle()
                )
        );

        return updatedRoom;
    }

    public ResponseRoomDto updateRoomName(final long roomId, final String roomName) {
        final ResponseRoomDto room = getRoomById(roomId);
        final String oldRoomName = room.getRoomName();

        if (oldRoomName.equals(roomName) || roomName.trim().isEmpty()) {
            return room;
        }

        room.setRoomName(roomName);

        final ResponseRoomDto updatedRoom = roomMapper.toDto(roomRepository.save(roomMapper.responseToEntity(room)));
        applicationEventPublisher.publishEvent(new RoomUpdatedEvent(updatedRoom));

        eventMessageService.createEventMessage(
                eventMessageFactory.createMessageRoomNameChanged(
                        room.getEvent().getId(),
                        room.getCreator().getId(),
                        oldRoomName,
                        roomName
                )
        );

        return updatedRoom;
    }

    public void deleteRoom(final Long id) {
        if (roomRepository.findById(id).isPresent()) {
            roomRepository.deleteById(id);
            applicationEventPublisher.publishEvent(new RoomDeletedEvent(id));
        } else {
            throw new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE());
        }
    }

    public void closeRoom(final Long id) {
        final ResponseRoomDto room = getRoomById(id);
        room.setIsActive(false);

        roomRepository.save(roomMapper.responseToEntity(room));

        applicationEventPublisher.publishEvent(new RoomClosedEvent(id));
    }

    public void associateStoriesWithRoom(final List<Story> stories, final Long roomId) {
        final Room room = roomMapper.responseToEntity(getRoomById(roomId));
        room.getStories().addAll(stories);

        roomRepository.save(room);
    }

    public Optional<Room> optionalRoomByCurrentStory(final Story story) {
        return roomRepository.findByCurrentStory(story);
    }

    private void setRoomCode(final Room room) {
        String code;

        do {
            code = roomCodeGeneration.generateCode();
        } while (roomRepository.existsRoomByRoomCode(code));

        room.setRoomCode(code);
    }

    private void setCreator(final RequestRoomDto room, final String userEmail) {
        room.setCreator(userService.getUserByEmail(userEmail));
    }

    private void setStories(final List<ResponseStoryDto> stories, final Room room) {
        room.setStories(stories.stream().map(storyMapper::responseDtoToEntity).toList());
    }

    private void setRoomSpectatorRoleForInvitedUsers(final Room room) {
        final List<RoomUserRole> newRoles = new ArrayList<>();

        room.getInvitedUsers().forEach(user -> {
            final RoomUserRole roomUserRole = new RoomUserRole();
            roomUserRole.setRoom(room);
            roomUserRole.setUser(user);
            roomUserRole.setRole(Role.USER_SPECTATOR);

            newRoles.add(roomUserRole);
        });

        final List<RoomUserRole> savedRoles = userRoleService.createSeveralRoles(newRoles);

        room.getInvitedUsers().forEach(user -> savedRoles.stream()
                .filter(savedRole -> savedRole.getUser().getId().equals(user.getId()))
                .findFirst()
                .ifPresent(user.getRoles()::add)
        );
    }

    private void updateEvent(final Room room) {
        room.getEvent().setRoom(room);
        eventMessageService.createEventMessage(eventMessageFactory.createMessageRoomCreated(
                room.getEvent().getId(), room.getCreator().getId(),
                LocalDateTime.now().toString(), room.getCreator().getFirstName()));
    }
}
