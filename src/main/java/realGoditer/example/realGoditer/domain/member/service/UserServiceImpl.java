package realGoditer.example.realGoditer.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import realGoditer.example.realGoditer.domain.member.dao.UserRepository;
import realGoditer.example.realGoditer.domain.member.domain.Role;
import realGoditer.example.realGoditer.domain.member.domain.User;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User updateUser(Long id, Role role, Long pay) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("doesn't exist user"));

        user.setPay(pay);
        user.setRole(role);

        return userRepository.save(user);
    }
}
