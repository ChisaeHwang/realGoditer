package realGoditer.example.realGoditer.domain.member.service;

import org.springframework.stereotype.Service;
import realGoditer.example.realGoditer.domain.member.domain.User;

@Service
public class LoginService {
    public void socialLogin(String code, String registrationId) {
        System.out.println("code = " + code);
        System.out.println("registrationId = " + registrationId);
    }

    public User findBySubject(String subject) {
        return null;
    }
}
