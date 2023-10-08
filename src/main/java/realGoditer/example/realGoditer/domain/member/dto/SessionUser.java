package realGoditer.example.realGoditer.domain.member.dto;


import lombok.Getter;
import realGoditer.example.realGoditer.domain.member.domain.Email;
import realGoditer.example.realGoditer.domain.member.domain.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getUsername();
        this.email = user.getEmail().getValue();
        this.picture = user.getPicture();
    }
}
