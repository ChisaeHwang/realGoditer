package realGoditer.example.realGoditer.global.config.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import realGoditer.example.realGoditer.global.config.jwt.JwtTokenProvider;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;

    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String token = tokenProvider.generateToken(authentication);

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
}

