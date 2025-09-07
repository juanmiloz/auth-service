package co.com.pragma.model.user.error;

import co.com.pragma.model.shared.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginErrorCode implements ErrorCode {

    INVALID_CREDENTIALS("LGN_001", 401, "Invalid username or password.");

    private final String appCode;
    private final int httpCode;
    private final String message;

}
