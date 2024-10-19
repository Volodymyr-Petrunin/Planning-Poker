package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.generation.RoomCodeGeneration;
import planing.poker.domain.Room;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.domain.dto.response.ResponseStoryDto;
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

    @Autowired
    public RoomService(final RoomRepository roomRepository, final RoomMapper roomMapper,
                       final RoomCodeGeneration roomCodeGeneration, final UserService userService,
                       final StoryService storyService, final StoryMapper storyMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.roomCodeGeneration = roomCodeGeneration;
        this.userService = userService;
        this.storyService = storyService;
        this.storyMapper = storyMapper;
    }

    public ResponseRoomDto createRoom(final RequestRoomDto roomDto, final String userEmail) {
        setCreator(roomDto, userEmail);
        final List<ResponseStoryDto> stories = storyService.createSeveralStory(roomDto.getStories());

        final Room room = roomMapper.toEntity(roomDto);
        setRoomCode(room);
        setStories(stories, room);
        room.setIsActive(true);
        room.setIsVotingOpen(false);

        return roomMapper.toDto(roomRepository.save(room));
    }

    public List<ResponseRoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(roomMapper::toDto).toList();
    }

    public ResponseRoomDto getRoomById(final Long id) {
        return roomRepository.findById(id).map(roomMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object"));
    }

    public ResponseRoomDto getRoomByCode(final String roomCode) {
        return roomMapper.toDto(roomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object")));
    }

    public ResponseRoomDto updateRoom(long id, final RequestRoomDto requestRoomDto) {
        if (roomRepository.findById(id).isPresent()) {
            final Room room = roomMapper.toEntity(requestRoomDto);
            room.setId(id);

            return roomMapper.toDto(roomRepository.save(room));
        } else {
            throw new IllegalArgumentException("message.not.find.object");
        }
    }

    public void deleteRoom(final Long id) {
        roomRepository.deleteById(id);
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
