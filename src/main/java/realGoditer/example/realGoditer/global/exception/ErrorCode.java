package realGoditer.example.realGoditer.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // API에서 발생할 수 있는 에러
    NICKNAME_DUPLICATION(409,"U001","Nickname Duplication Exception"),
    NOT_FULLY_JOINED(401,"U002", "You did not sign up for details, Please continue to signup"),
    USER_NOT_FOUND(400,"U003", "user not found exception"),
    UNAUTHORIZED(401, "C401", "Unauthorized"),
    FORBIDDEN(403, "C403", "Forbidden"),
    ;

    private final String code;
    private final String message;
    private int status;
    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
