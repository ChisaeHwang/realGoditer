package realGoditer.example.realGoditer.domain.member.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import realGoditer.example.realGoditer.domain.member.annotation.Authenticated;
import realGoditer.example.realGoditer.domain.member.annotation.Member;
import realGoditer.example.realGoditer.domain.member.domain.AuthPrincipal;
import realGoditer.example.realGoditer.global.config.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;

    public UserController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Member
    @GetMapping("/getUserEmail")
    public ResponseEntity<String> getUserEmail(@Authenticated AuthPrincipal authPrincipal) {
        log.info("토큰 메일" + authPrincipal.getEmail());
        return ResponseEntity.ok(authPrincipal.getEmail());
    }



}




