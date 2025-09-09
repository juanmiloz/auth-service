package co.com.pragma.model.shared.exception;

import co.com.pragma.model.user.error.UserErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DomainExceptionFactoryTest {

    @Test
    @DisplayName("Should format messages with placeholders")
    void format_with_placeholders() {
        DomainException ex = DomainExceptionFactory.exceptionOf(
                UserErrorCode.INVALID_EMAIL, "malCorreo"
        );

        assertThat(ex.getErrorCode()).isEqualTo(UserErrorCode.INVALID_EMAIL);
        assertThat(ex.getMessage()).isEqualTo("Invalid email format: malCorreo");
        assertThat(ex.getHttpCode()).isEqualTo(400);
        assertThat(ex.getAppCode()).isEqualTo("USR_005");
    }

    @Test
    @DisplayName("EMAIL_IN_USE should wrap the value with single quotes (MessageFormat)")
    void email_in_use_quotes() {
        DomainException ex = DomainExceptionFactory.exceptionOf(
                UserErrorCode.EMAIL_IN_USE, "test@mail.com"
        );

        assertThat(ex.getMessage()).isEqualTo("Email 'test@mail.com' is already in use.");
        assertThat(ex.getErrorCode()).isEqualTo(UserErrorCode.EMAIL_IN_USE);
    }

}