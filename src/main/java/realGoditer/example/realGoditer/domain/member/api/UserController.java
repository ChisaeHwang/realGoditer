package realGoditer.example.realGoditer.domain.member.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import realGoditer.example.realGoditer.global.config.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;

    public UserController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/getUserEmail")
    public ResponseEntity<String> getUserEmail(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        log.info(token);

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token provided");
        }

        try {
            String email = jwtTokenProvider.getSubjectFromToken(token.substring(7));
            return ResponseEntity.ok(email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}




