package realGoditer.example.realGoditer.global.exception;

import org.springframework.http.HttpStatus;

public class NotFoundUserException extends RuntimeException {

    private final HttpStatus httpStatus;

    public NotFoundUserException() {
        super("존재하지 않는 회원입니다,");
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
