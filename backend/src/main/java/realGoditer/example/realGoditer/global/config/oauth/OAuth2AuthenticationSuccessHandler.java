package realGoditer.example.realGoditer.global.config.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.PrintWriter;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final String signupUrl;
    private final String homeUrl;

    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider,
                                              ObjectMapper objectMapper,
                                              UserRepository userRepository,
                                              @Value("${redirect.signup-url}") String signupUrl,
                                              @Value("${redirect.home-url}") String homeUrl) {
        this.tokenProvider = tokenProvider;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.signupUrl = signupUrl;
        this.homeUrl = homeUrl;
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

        String targetUrl;
        if(user.getRole().isInitial()) {
            targetUrl = signupUrl + "?token=" + token;
        } else {
            targetUrl = homeUrl + "?token=" + token;
        }

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

