package realGoditer.example.realGoditer.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;
import realGoditer.example.realGoditer.domain.member.domain.Email;
import realGoditer.example.realGoditer.domain.member.domain.Member;
import realGoditer.example.realGoditer.domain.member.domain.Role;

import java.util.Map;

@Getter
public class OAuthAttributes {  //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스이다.
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(Email.from(email))
                .role(Role.USER)
                .build();
    }
}
