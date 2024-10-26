package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.Messages;
import planing.poker.common.generation.RoomCodeGeneration;
import planing.poker.domain.Room;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.domain.dto.response.ResponseStoryDto;
import planing.poker.event.room.RoomCreatedEvent;
import planing.poker.event.room.RoomCurrentStoryEvent;
import planing.poker.event.room.RoomUpdatedEvent;
import planing.poker.event.story.StoryDeletedEvent;
import planing.poker.mapper.RoomMapper;
import planing.poker.mapper.StoryMapper;
import planing.poker.repository.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    private final StoryMapper storyMapper;

    private final RoomCodeGeneration roomCodeGeneration;

    private final UserService userService;

    private final StoryService storyService;

    private final Messages messages;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public RoomService(final RoomRepository roomRepository, final RoomMapper roomMapper,
                       final RoomCodeGeneration roomCodeGeneration, final UserService userService,
                       final StoryService storyService, final StoryMapper storyMapper, final Messages messages,
                       final ApplicationEventPublisher applicationEventPublisher) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.roomCodeGeneration = roomCodeGeneration;
        this.userService = userService;
        this.storyService = storyService;
        this.storyMapper = storyMapper;
        this.messages = messages;
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

        final ResponseRoomDto savedRoom = roomMapper.toDto(roomRepository.save(room));
        applicationEventPublisher.publishEvent(new RoomCreatedEvent(savedRoom));

        return savedRoom;
    }

    public List<ResponseRoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(roomMapper::toDto).toList();
    }

    public ResponseRoomDto getRoomById(final Long id) {
        return roomRepository.findById(id).map(roomMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException(messages.NO_FIND_MESSAGE()));
    }

    public ResponseRoomDto getRoomByCode(final String roomCode) {
        return roomMapper.toDto(roomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException(messages.NO_FIND_MESSAGE())));
    }

    public ResponseRoomDto updateRoom(long id, final RequestRoomDto requestRoomDto) {
        if (roomRepository.findById(id).isPresent()) {
            final Room room = roomMapper.toEntity(requestRoomDto);
            room.setId(id);

            final ResponseRoomDto updatedRoom = roomMapper.toDto(roomRepository.save(room));
            applicationEventPublisher.publishEvent(new RoomUpdatedEvent(updatedRoom));

            return updatedRoom;
        } else {
            throw new IllegalArgumentException(messages.NO_FIND_MESSAGE());
        }
    }

    public ResponseRoomDto updateCurrentStory(final long roomId, final ResponseStoryDto responseStoryDto) {
        final ResponseRoomDto room = getRoomById(roomId);
        room.setCurrentStory(responseStoryDto);

        final ResponseRoomDto updatedRoom = roomMapper.toDto(roomRepository.save(roomMapper.responseToEntity(room)));
        applicationEventPublisher.publishEvent(new RoomCurrentStoryEvent(updatedRoom));

        return updatedRoom;
    }

    public void deleteRoom(final Long id) {
        if (roomRepository.findById(id).isPresent()) {
            roomRepository.deleteById(id);
            applicationEventPublisher.publishEvent(new StoryDeletedEvent(id));
        } else {
            throw new IllegalArgumentException(messages.NO_FIND_MESSAGE());
        }
    }

    private void setRoomCode(final Room room) {
        String code;

        do {
            code = roomCodeGeneration.generateCode();
        } while (roomRepository.existsRoomByRoomCode(code));

        room.setRoomCode(code);
    }

    private void setCreator(final RequestRoomDto room,final String userEmail) {
        room.setCreator(userService.getUserByEmail(userEmail));
    }

    private void setStories(final List<ResponseStoryDto> stories, final Room room) {
        room.setStories(stories.stream().map(storyMapper::responseDtoToEntity).toList());
    }
}
