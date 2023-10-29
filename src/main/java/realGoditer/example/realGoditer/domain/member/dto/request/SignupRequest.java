package realGoditer.example.realGoditer.domain.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import realGoditer.example.realGoditer.domain.member.domain.Role;


@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignupRequest {

    private String name;

    private Long pay;

    private Role role;

    public static SignupRequest of(
            final String name,
            final Long pay,
            final Role role) {
        return new SignupRequest(name, pay, role);
    }


}
