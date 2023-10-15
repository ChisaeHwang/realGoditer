package realGoditer.example.realGoditer.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // API 경로 패턴
                .allowedOrigins("http://localhost:3000")  // 허용할 도메인
                .allowedMethods("*")  // 허용할 HTTP 메서드
                .allowedHeaders("*")  // 허용할 헤더
                .allowCredentials(true)  // 쿠키를 포함할지 여부
                .maxAge(3600);  // 사전 요청(Preflight)의 결과 캐시 시간
    }
}

