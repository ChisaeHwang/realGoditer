package realGoditer.example.realGoditer.domain.member.domain;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN, USER;

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
