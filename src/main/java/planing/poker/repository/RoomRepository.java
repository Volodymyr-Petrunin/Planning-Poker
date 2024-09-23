package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}