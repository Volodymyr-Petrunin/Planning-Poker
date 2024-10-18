package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.generation.RoomCodeGeneration;
import planing.poker.domain.Room;
import planing.poker.domain.dto.response.ResponseRoomDto;
import planing.poker.domain.dto.request.RequestRoomDto;
import planing.poker.mapper.RoomMapper;
import planing.poker.repository.RoomRepository;

import java.util.List;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    private final RoomCodeGeneration roomCodeGeneration;

    @Autowired
    public RoomService(final RoomRepository roomRepository, final RoomMapper roomMapper,
                       final RoomCodeGeneration roomCodeGeneration) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.roomCodeGeneration = roomCodeGeneration;
    }

    public ResponseRoomDto createRoom(final RequestRoomDto roomDto) {
        final Room room = roomMapper.toEntity(roomDto);
        setRoomCode(room);

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
}
