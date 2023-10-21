package realGoditer.example.realGoditer.domain.member.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realGoditer.example.realGoditer.domain.member.annotation.Authenticated;
import realGoditer.example.realGoditer.domain.member.annotation.Member;
import realGoditer.example.realGoditer.domain.member.domain.AuthPrincipal;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.member.dto.request.SignupRequest;
import realGoditer.example.realGoditer.domain.member.dto.response.SignupResponse;
import realGoditer.example.realGoditer.domain.member.service.UserService;
import realGoditer.example.realGoditer.global.config.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    @Member
    @GetMapping("/getUserEmail")
    public ResponseEntity<String> getUserEmail(@Authenticated AuthPrincipal authPrincipal) {
        log.info("토큰 메일" + authPrincipal.getEmail());
        return ResponseEntity.ok(authPrincipal.getEmail());
    }

    @Member
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signupUser(@Authenticated AuthPrincipal authPrincipal,
                                                     @Valid @RequestBody final SignupRequest request) {
        log.info(String.valueOf(request.getRole()));
        log.info(String.valueOf(request.getPay()));
        User user = userService.updateUser(authPrincipal.getId(), request.getRole(), request.getPay());
        return ResponseEntity.ok(SignupResponse.of(user));
    }



}




