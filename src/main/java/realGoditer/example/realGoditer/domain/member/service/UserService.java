package realGoditer.example.realGoditer.domain.member.service;

import realGoditer.example.realGoditer.domain.member.domain.Role;
import realGoditer.example.realGoditer.domain.member.domain.User;

public interface UserService {

    User updateUser(Long id, Role role, Long pay);

}
