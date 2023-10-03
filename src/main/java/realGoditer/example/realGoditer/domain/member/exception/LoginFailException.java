package realGoditer.example.realGoditer.domain.member.exception;

import org.springframework.http.HttpStatus;

public class LoginFailException extends RuntimeException{

    private final HttpStatus httpStatus;

    public LoginFailException() {
        super("이메일 혹은 비밀번호를 잘못 입력하셨습니다.");
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
