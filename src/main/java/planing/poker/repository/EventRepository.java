package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}