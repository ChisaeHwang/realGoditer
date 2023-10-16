package realGoditer.example.realGoditer.domain.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;

    @Builder
    public User(Long id, String name, String email, String password, Role role, String provider) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.provider = provider;
    }

    public User update(String name, String provider) {
        this.name = name;
        this.provider = provider;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
