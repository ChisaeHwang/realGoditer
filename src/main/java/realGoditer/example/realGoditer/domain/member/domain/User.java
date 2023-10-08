package realGoditer.example.realGoditer.domain.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import realGoditer.example.realGoditer.domain.member.domain.converter.EmailConverter;
import realGoditer.example.realGoditer.domain.member.domain.converter.PasswordConverter;
import realGoditer.example.realGoditer.domain.member.exception.LoginFailException;
import realGoditer.example.realGoditer.domain.member.security.PasswordEncoder;

import java.security.Timestamp;
import java.util.Objects;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    @Convert(converter = EmailConverter.class)
    private Email email;

    @Column(name = "password", nullable = false)
    @Convert(converter = PasswordConverter.class)
    private EncodedPassword encodedPassword;

    @Column(name = "name", nullable = false, length = 255)
    private String username;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;

    // 추가됨
    private String provider;
    private String providerId;

    @CreationTimestamp
    private Timestamp createDate;


    @Builder
    private User(
            final Email email,
            final EncodedPassword encodedPassword,
            final String name,
            final String picture,
            final Role role,
            final String provider,
            final String providerId,
            final Timestamp createDate
    ) {
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.username = name;
        this.picture = picture;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }

    public static User of(
            final String email,
            final EncodedPassword encodedPassword,
            final String name,
            final String picture,
            final String provider,
            final String providerId,
            final Timestamp createDate
    ) {
        return new User(Email.from(email)
                , encodedPassword
                , name
                , picture
                , Role.USER
                , provider
                , providerId
                , createDate);
    }

    public void validatePassword(final String password, final PasswordEncoder passwordEncoder) {
        if (!encodedPassword.isMatch(password, passwordEncoder)) {
            throw new LoginFailException();
        }
    }

    public User update(String name, String picture) {
        this.username = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        final User member = (User) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email=" + email +
                ", encodedPassword=" + encodedPassword +
                ", name='" + username + '\'' +
                '}';
    }

    public String getPassword() {
        return this.encodedPassword.getValue();
    }
}
