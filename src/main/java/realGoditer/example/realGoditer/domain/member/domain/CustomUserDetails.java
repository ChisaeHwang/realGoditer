package realGoditer.example.realGoditer.domain.member.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private Long id;  // 사용자의 데이터베이스 ID
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // 생성자, getter, setter 등 기본 메서드는 생략

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // or your custom logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // or your custom logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // or your custom logic
    }

    @Override
    public boolean isEnabled() {
        return true;  // or your custom logic
    }

    // 사용자의 데이터베이스 ID를 반환하는 getter 메서드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
