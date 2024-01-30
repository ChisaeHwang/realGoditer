package realGoditer.example.realGoditer.global.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import realGoditer.example.realGoditer.domain.member.dao.UserRepository;
import realGoditer.example.realGoditer.domain.member.domain.AuthPrincipal;
import realGoditer.example.realGoditer.global.config.jwt.JwtTokenProvider;
import realGoditer.example.realGoditer.global.exception.NotFoundUserException;
import realGoditer.example.realGoditer.global.exception.TokenInvalidException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String USER_KEY = "userId";
    private static final String BEARER_TYPE = "Bearer";

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler
    ) {
        if (isEmptyToken(request)) {
            return true;
        }

        String accessToken = getAccessToken(request);
        validateToken(accessToken);

        AuthPrincipal authPrincipal = getAuthPrincipal(accessToken);
        validateMember(authPrincipal.getId());
        request.setAttribute(USER_KEY, authPrincipal);

        return true;
    }

    private boolean isEmptyToken(HttpServletRequest request) {
        return !StringUtils.hasText(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    private String getAccessToken(final HttpServletRequest request) {
        String value = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(value) || !value.startsWith(BEARER_TYPE)) {
            log.info("헤더에 토큰이 없습니다. value: {}", value);
            throw new TokenInvalidException();
        }

        return value.substring(BEARER_TYPE.length()).trim();
    }

    private void validateToken(final String accessToken) {
        if (!tokenProvider.validateToken(accessToken)) {
            log.info("토큰이 유효하지 않습니다. accessToken: {}", accessToken);
            throw new TokenInvalidException();
        }
    }

    private AuthPrincipal getAuthPrincipal(String accessToken) {
        try {
            return objectMapper.readValue(tokenProvider.getPayload(accessToken),
                    AuthPrincipal.class);
        } catch (JsonProcessingException e) {
            log.error("AuthPrincipal 변환 중 에러가 발생했습니다. accessToken: {}", accessToken, e);
            throw new TokenInvalidException();
        }
    }

    private void validateMember(final Long memberId) {
        if (!userRepository.existsById(memberId)) {
            log.info("존재하지 않는 회원입니다. memberId: {}", memberId);
            throw new NotFoundUserException();
        }
    }
}
