package co.com.pragma.model.user.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
class UserErrorCodeTest {

    @Test
    @DisplayName("Should format messages with placeholders")
    void all_have_values() {
        for (UserErrorCode code : UserErrorCode.values()) {
            assertThat(code.getAppCode()).isNotBlank();
            assertThat(code.getHttpCode()).isBetween(100, 599);
            assertThat(code.getMessage()).isNotBlank();
        }
    }

    @Test
    @DisplayName("EMAIL_IN_USE should wrap the value with single quotes (MessageFormat)")
    void specific_values() {
        assertThat(UserErrorCode.REQUIRED_FIRSTNAME.getAppCode()).isEqualTo("USR_001");
        assertThat(UserErrorCode.USER_NOT_FOUND.getHttpCode()).isEqualTo(404);
        assertThat(UserErrorCode.INVALID_SALARY.getMessage()).contains("between 0 and 15000000");
    }

}