package realGoditer.example.realGoditer.domain.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import realGoditer.example.realGoditer.domain.member.domain.Member;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
