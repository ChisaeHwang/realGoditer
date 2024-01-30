package realGoditer.example.realGoditer.domain.member.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import realGoditer.example.realGoditer.domain.member.annotation.Authenticated;
import realGoditer.example.realGoditer.domain.member.annotation.Member;
import realGoditer.example.realGoditer.domain.member.domain.AuthPrincipal;
import realGoditer.example.realGoditer.domain.member.domain.User;
import realGoditer.example.realGoditer.domain.member.dto.request.SignupRequest;
import realGoditer.example.realGoditer.domain.member.dto.response.SignupResponse;
import realGoditer.example.realGoditer.domain.member.dto.response.UserResponse;
import realGoditer.example.realGoditer.domain.member.service.UserService;
import realGoditer.example.realGoditer.global.dto.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    @Member
    @GetMapping("/user")
    public ApiResponse<UserResponse> getUser(@Authenticated AuthPrincipal authPrincipal) {
        User user = userService.findUser(authPrincipal.getId());
        return ApiResponse.success(UserResponse.of(user), 200);
    }

    @GetMapping("/user/all")
    public ApiResponse<List<UserResponse>> getAllUser() {
        List<UserResponse> responses = userService.findAll().stream()
                .map(UserResponse::of) // User 객체를 UserResponse 객체로 변환
                .collect(Collectors.toList()); // 스트림을 리스트로 수집

        return ApiResponse.success(responses, 200);
    }


    @Member
    @PostMapping("/signup")
    public ApiResponse<SignupResponse> signupUser(@Authenticated AuthPrincipal authPrincipal,
                                                  @Valid @RequestBody final SignupRequest request) {
        User user = userService.updateUser(authPrincipal.getId(), request);
        return ApiResponse.success(SignupResponse.of(user), 200);
    }


}




