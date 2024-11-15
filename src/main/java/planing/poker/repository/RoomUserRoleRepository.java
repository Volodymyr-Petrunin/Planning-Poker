package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planing.poker.domain.RoomUserRole;

@Repository
public interface RoomUserRoleRepository extends JpaRepository<RoomUserRole, Long> {
}
