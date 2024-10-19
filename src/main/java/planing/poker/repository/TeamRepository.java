package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
