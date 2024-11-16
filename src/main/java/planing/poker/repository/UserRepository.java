package planing.poker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import planing.poker.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(final String email);

    boolean existsByEmail(final String email);
}