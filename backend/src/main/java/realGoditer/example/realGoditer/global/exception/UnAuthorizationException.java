package realGoditer.example.realGoditer.global.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizationException extends RuntimeException {

    private final HttpStatus httpStatus;

    public UnAuthorizationException() {
        super("로그인이 필요합니다");
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
