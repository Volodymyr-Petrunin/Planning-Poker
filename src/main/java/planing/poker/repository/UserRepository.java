package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}