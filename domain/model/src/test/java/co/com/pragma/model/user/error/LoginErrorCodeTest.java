package co.com.pragma.model.user.error;

import co.com.pragma.model.shared.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginErrorCodeTest {

    @Test
    @DisplayName("INVALID_CREDENTIALS expone metadatos correctos")
    void metadata() {
        LoginErrorCode code = LoginErrorCode.INVALID_CREDENTIALS;

        assertThat(code.getAppCode()).isEqualTo("LGN_001");
        assertThat(code.getHttpCode()).isEqualTo(401);
        assertThat(code.getMessage()).isEqualTo("Invalid username or password.");
    }

    @Test
    @DisplayName("values() y valueOf() funcionan; implementa ErrorCode")
    void valuesAndInterface() {
        assertThat(LoginErrorCode.values()).hasSize(1);
        assertThat(LoginErrorCode.valueOf("INVALID_CREDENTIALS"))
                .isSameAs(LoginErrorCode.INVALID_CREDENTIALS);
        assertThat(LoginErrorCode.INVALID_CREDENTIALS).isInstanceOf(ErrorCode.class);
    }

}