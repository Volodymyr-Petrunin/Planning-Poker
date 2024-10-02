package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {
}