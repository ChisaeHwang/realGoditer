package realGoditer.example.realGoditer.domain.member.security;

public interface PasswordEncoder {

    boolean matches(final String planePassword, final String encodedPassword);

    String encode(final String password);
}
