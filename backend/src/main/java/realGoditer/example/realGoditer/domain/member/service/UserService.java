package realGoditer.example.realGoditer.domain.member.service;

import realGoditer.example.realGoditer.domain.member.domain.Role;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.member.dto.request.SignupRequest;

import java.util.List;

public interface UserService {

    User updateUser(Long id, SignupRequest request);

    User findUser(Long id);

    List<User> findAll();

}
