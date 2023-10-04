package realGoditer.example.realGoditer.domain.member.dto;


import lombok.Getter;
import realGoditer.example.realGoditer.domain.member.domain.Email;
import realGoditer.example.realGoditer.domain.member.domain.Member;

@Getter
public class SessionUser {
    private String name;
    private Email email;

    public SessionUser(Member user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
