package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}