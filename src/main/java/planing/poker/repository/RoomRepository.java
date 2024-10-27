package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.Room;
import planing.poker.domain.Story;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsRoomByRoomCode(String roomCode);

    Optional<Room> findByRoomCode(String roomCode);

    Optional<Room> findByCurrentStory(Story currentStory);
}