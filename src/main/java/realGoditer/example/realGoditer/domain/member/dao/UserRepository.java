package realGoditer.example.realGoditer.domain.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import realGoditer.example.realGoditer.domain.member.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findBySubject(String subject);
}
