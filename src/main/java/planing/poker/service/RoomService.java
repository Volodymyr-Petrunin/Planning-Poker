package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.domain.dto.RoomDto;
import planing.poker.mapper.RoomMapper;
import planing.poker.repository.RoomRepository;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    @Autowired
    public RoomService(final RoomRepository roomRepository, final RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public RoomDto createRoom(final RoomDto roomDto) {
        return roomMapper.toDto(roomRepository.save(roomMapper.toEntity(roomDto)));
    }

    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(roomMapper::toDto).toList();
    }

    public RoomDto getRoomById(final Long id) {
        return roomRepository.findById(id).map(roomMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object"));
    }

    public RoomDto updateRoom(final RoomDto roomDto) {
        return roomMapper.toDto(roomRepository.save(roomMapper.toEntity(roomDto)));
    }

    public void deleteRoom(final Long id) {
        roomRepository.deleteById(id);
    }
}
