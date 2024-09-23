package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.EventMessage;

public interface EventMessageRepository extends JpaRepository<EventMessage, Long> {
}