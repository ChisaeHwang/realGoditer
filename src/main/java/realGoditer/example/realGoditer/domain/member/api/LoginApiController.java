package realGoditer.example.realGoditer.domain.member.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import realGoditer.example.realGoditer.domain.member.service.LoginService;

@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class LoginApiController {

    @Autowired
    LoginService  loginService;

    @GetMapping("/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        loginService.socialLogin(code, registrationId);
    }
}
