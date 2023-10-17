package realGoditer.example.realGoditer.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import realGoditer.example.realGoditer.domain.member.annotation.Member;
import realGoditer.example.realGoditer.domain.member.domain.AuthPrincipal;
import realGoditer.example.realGoditer.global.exception.UnAuthorizationException;

import java.util.Objects;

@Component
public class UserAuthorityInterceptor implements HandlerInterceptor {

    private static final String USER_KEY = "userId";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Member member = handlerMethod.getMethodAnnotation(Member.class);
        if (!Objects.isNull(member)) {
            validateUserAuthorization(request);
        }

        return true;
    }

    private void validateUserAuthorization(final HttpServletRequest request) {
        AuthPrincipal authPrincipal = (AuthPrincipal)request.getAttribute(USER_KEY);
        if (Objects.isNull(authPrincipal)) {
            throw new UnAuthorizationException();
        }
    }
}
