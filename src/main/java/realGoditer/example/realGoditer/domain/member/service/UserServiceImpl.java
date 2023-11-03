package realGoditer.example.realGoditer.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import realGoditer.example.realGoditer.domain.member.dao.UserRepository;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.member.dto.request.SignupRequest;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User updateUser(Long id, SignupRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("doesn't exist user"));

        user.setName(request.getName());
        user.setPay(request.getPay());
        user.setRole(request.getRole());

        return userRepository.save(user);
    }


    @Override
    public User findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("doesn't exist user"));

        return user;
    }
}
