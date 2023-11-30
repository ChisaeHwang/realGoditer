package realGoditer.example.realGoditer.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import realGoditer.example.realGoditer.global.argument.AuthPrincipalResolver;
import realGoditer.example.realGoditer.global.interceptor.AuthenticationInterceptor;
import realGoditer.example.realGoditer.global.interceptor.UserAuthorityInterceptor;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final UserAuthorityInterceptor userAuthorityInterceptor;
    private final AuthPrincipalResolver authPrincipalResolver;

    @Value("${cors.allowed-origin}")
    private String allowedOrigin;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry
                .addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .order(0);

        registry
                .addInterceptor(userAuthorityInterceptor)
                .addPathPatterns("/**")
                .order(1);

    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authPrincipalResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://goditer-front.vercel.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}


