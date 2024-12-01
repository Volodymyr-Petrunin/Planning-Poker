package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.Story;
import planing.poker.domain.User;
import planing.poker.domain.Vote;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByVoterAndStory(User voter, Story story);
}