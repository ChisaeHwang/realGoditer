package realGoditer.example.realGoditer.global.exception;

import org.springframework.http.HttpStatus;

public class TokenInvalidException extends RuntimeException {

    private final HttpStatus httpStatus;

    public TokenInvalidException() {
        super("유효한 토큰이 아닙니다,");
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
