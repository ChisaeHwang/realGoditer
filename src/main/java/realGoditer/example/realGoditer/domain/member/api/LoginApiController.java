package realGoditer.example.realGoditer.domain.member.api;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realGoditer.example.realGoditer.domain.member.service.CustomOAuth2UserService;
import realGoditer.example.realGoditer.domain.member.service.LoginService;
import realGoditer.example.realGoditer.infra.OAuth.GoogleOAuth;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/login/oauth2", produces = "application/json")
@Slf4j
public class LoginApiController {

    private final LoginService loginService;

    private final CustomOAuth2UserService customOAuth2UserService;

//    private final GoogleOAuth googleOAuth;


    @GetMapping("/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        loginService.socialLogin(code, registrationId);
    }

//    @GetMapping("/code/{registrationId}")
//    public ResponseEntity<String> googleLogin(@RequestParam String code, @PathVariable String registrationId) {
//        log.info(code);
//        log.info(registrationId);
//        loginService.socialLogin(code, registrationId);
//        return googleOAuth.getGoogleAccessToken(code);
//    }

//    @GetMapping("/google")
//    public void getGoogleAuthUrl(HttpServletResponse response) throws Exception {
//        log.info(googleOAuth.getGoogleRedirectUrl());
//        response.sendRedirect(googleOAuth.getGoogleRedirectUrl());
//    }
}