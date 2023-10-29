package realGoditer.example.realGoditer.domain.member.service;

import realGoditer.example.realGoditer.domain.member.domain.Role;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.member.dto.request.SignupRequest;

public interface UserService {

    User updateUser(Long id, SignupRequest request);

}
