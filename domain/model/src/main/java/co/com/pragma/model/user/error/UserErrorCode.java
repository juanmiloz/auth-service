package co.com.pragma.model.user.error;

import co.com.pragma.model.shared.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * User-related error codes with HTTP status and message templates.
 */
@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    REQUIRED_FIRSTNAME      ("USR_001", 400, "Firstname is required."),
    REQUIRED_LASTNAME       ("USR_002", 400, "Lastname is required."),
    REQUIRED_EMAIL          ("USR_003", 400, "Email is required."),
    REQUIRED_SALARY         ("USR_004", 400, "Base salary is required."),
    INVALID_EMAIL           ("USR_005", 400, "Invalid email format: {0}"),
    INVALID_SALARY          ("USR_006", 400, "Base salary must be between 0 and 15000000."),
    EMAIL_IN_USE            ("USR_007", 409, "Email ''{0}'' is already in use."),
    USER_NOT_FOUND          ("USR_008", 404, "User not found with id {0}"),
    USER_NOT_FOUND_BY_EMAIL ("USR_008", 404, "User not found with email {0}");

    private final String appCode;
    private final int httpCode;
    private final String message;

}
