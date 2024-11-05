package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import planing.poker.domain.Room;
import planing.poker.domain.Story;
import planing.poker.domain.User;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsRoomByRoomCode(String roomCode);

    Optional<Room> findByRoomCode(String roomCode);

    Optional<Room> findByCurrentStory(Story currentStory);

    @Query("SELECT r FROM rooms r WHERE r.creator = :user OR :user MEMBER OF r.invitedUsers")
    List<Room> findRoomsRelatedToUser(@Param("user") User user);
}