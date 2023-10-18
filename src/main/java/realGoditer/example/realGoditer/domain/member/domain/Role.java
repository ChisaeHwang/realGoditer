package realGoditer.example.realGoditer.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "어드민"),
    USER("ROLE_USER", "사용자"),
    INITIAL("ROLE_INITIAL", "초기 사용자");

    private final String key;
    private final String title;

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public boolean isInitial() {
        return this == INITIAL;
    }
}

