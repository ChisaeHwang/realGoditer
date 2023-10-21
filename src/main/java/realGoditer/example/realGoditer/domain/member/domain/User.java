package realGoditer.example.realGoditer.domain.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realGoditer.example.realGoditer.domain.task.domain.TaskList;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Long pay;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TaskList> taskLists = new ArrayList<>();

    private String provider;

    // Protected constructor for builder
    @Builder
    protected User(String name, String email, String password, Long pay, Role role, String provider) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.pay = pay;
        this.role = role;
        this.provider = provider;
    }

    // Static factory method
    public static User of(
            String name, 
            String email, 
            String password, 
            Long pay, 
            Role role,
            String provider) {
        return new User(name, email, password, pay, role, provider);
    }

    public User update(String name, String provider) {
        this.name = name;
        this.provider = provider;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPay(Long pay) {
        this.pay = pay;
    }
}
