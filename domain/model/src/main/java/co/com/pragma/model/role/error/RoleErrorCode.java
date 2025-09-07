package co.com.pragma.model.role.error;

import co.com.pragma.model.shared.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleErrorCode implements ErrorCode {

    ROLE_NOT_FOUND      ("ROL_001", 404, "Role not found with id {0}");

    private final String appCode;
    private final int httpCode;
    private final String message;

}
