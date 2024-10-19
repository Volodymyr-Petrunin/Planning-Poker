package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsRoomByRoomCode(String roomCode);

    Optional<Room> findByRoomCode(String roomCode);
}