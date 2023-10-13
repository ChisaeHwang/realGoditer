package realGoditer.example.realGoditer.domain.member.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Jwts;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.member.service.CustomOAuth2UserService;
import realGoditer.example.realGoditer.global.config.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private CustomOAuth2UserService loginService;  // 사용자 정보를 가져오는 서비스

    @Autowired
    private JwtTokenProvider jwtTokenProvider;  // JWT 처리를 위한 클래스

    @Value("${jwt.secret}")
    private String YOUR_SECRET_KEY;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getUserEmail")
    public String getUserEmail(@RequestHeader("Authorization") String token) {
        // 토큰에서 식별자(subject) 추출
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String subject = jwtTokenProvider.getAuthentication(token).getName();

        // 식별자를 통해 사용자 정보 조회
        User user = loginService.findBySubject(subject);  // findBySubject는 예시 메서드입니다. 실제로 구현해야 할 수도 있습니다.

        log.info(user.getEmail());

        return user.getEmail();  // 사용자의 이메일 반환
    }

    private String getSubjectFromToken(String token) {
        // JwtTokenProvider 또는 직접 JWT 파싱 코드 사용
        String userEmail = Jwts.parser()
                .setSigningKey(YOUR_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return userEmail; // 여기서 subject는 이메일이 됩니다.
    }
}
