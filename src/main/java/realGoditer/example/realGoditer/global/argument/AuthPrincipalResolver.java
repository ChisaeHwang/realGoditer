package realGoditer.example.realGoditer.global.argument;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import realGoditer.example.realGoditer.domain.member.annotation.Authenticated;
import realGoditer.example.realGoditer.domain.member.domain.AuthPrincipal;

@Component
public class AuthPrincipalResolver implements HandlerMethodArgumentResolver {

    private static final String USER_KEY = "userId";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthPrincipal.class)
                && parameter.hasParameterAnnotation(Authenticated.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        return getAuthPrincipal(webRequest);
    }

    private AuthPrincipal getAuthPrincipal(final NativeWebRequest webRequest) {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (httpServletRequest == null) {
            throw new IllegalStateException();
        }
        if (httpServletRequest.getAttribute(USER_KEY) == null) {
            throw new IllegalStateException();
        }
        return (AuthPrincipal)httpServletRequest.getAttribute(USER_KEY);
    }
}
