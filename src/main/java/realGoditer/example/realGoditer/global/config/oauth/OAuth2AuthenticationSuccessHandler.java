package realGoditer.example.realGoditer.global.config.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import realGoditer.example.realGoditer.domain.member.dao.UserRepository;
import realGoditer.example.realGoditer.domain.member.domain.AuthPrincipal;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.member.exception.LoginFailException;
import realGoditer.example.realGoditer.global.config.jwt.JwtTokenProvider;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider, ObjectMapper objectMapper, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // Authentication 객체에서 사용자 정보 추출 (예: 이메일)
        String userEmail = ((OAuth2User) authentication.getPrincipal()).getAttribute("email");

        // DB에서 사용자 정보 조회
        User user = getMemberByEmail(userEmail);
        AuthPrincipal authPrincipal = AuthPrincipal.from(user);

        // AuthPrincipal을 사용해 토큰 생성
        String token = tokenProvider.generateToken(principalToJson(authPrincipal));

        // JWT 토큰을 쿠키에 설정
        Cookie jwtCookie = new Cookie("jwt_token", token);
        jwtCookie.setHttpOnly(false); // true는 JS에서 쿠키에 접근할 수 없도록 설정
        jwtCookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 유효 기간을 7일로 설정
        jwtCookie.setPath("/"); // 쿠키가 전송되는 경로 설정
        // jwtCookie.setSecure(true); // HTTPS를 사용할 경우 활성화하세요.
        response.addCookie(jwtCookie);

        String targetUrl = "http://localhost:3000";
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String principalToJson(final AuthPrincipal authPrincipal) {
        try {
            return objectMapper.writeValueAsString(authPrincipal);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private User getMemberByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(LoginFailException::new);
    }
}

