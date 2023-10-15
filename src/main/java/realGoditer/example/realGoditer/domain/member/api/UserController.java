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
    public String logTokenInfo(@RequestHeader("Authorization") String tokenHeader) {
        // 보통 토큰 앞에 "Bearer "가 붙어 있기 때문에 제거
        String token = tokenHeader.replace("Bearer ", "");

        // 토큰에서 클레임 정보 추출
        String userId = jwtTokenProvider.getAuthentication(token).getName();
        String authorities = jwtTokenProvider.getAuthentication(token).getAuthorities().toString();

        // 로그 출력
        log.info("User ID from token: {}", userId);
        log.info("Authorities from token: {}", authorities);

        return "Token info logged!";
    }

}
