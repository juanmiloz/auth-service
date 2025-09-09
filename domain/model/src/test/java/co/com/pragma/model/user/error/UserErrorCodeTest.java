package co.com.pragma.model.user.error;

import co.com.pragma.model.shared.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
class UserErrorCodeTest {

    @Test
    @DisplayName("Each constant present appCode, httpCode and message well")
    void metadataByConstant() {
        Map<UserErrorCode, String> appCodes = new EnumMap<>(UserErrorCode.class);
        appCodes.put(UserErrorCode.REQUIRED_FIRSTNAME, "USR_001");
        appCodes.put(UserErrorCode.REQUIRED_LASTNAME, "USR_002");
        appCodes.put(UserErrorCode.REQUIRED_EMAIL, "USR_003");
        appCodes.put(UserErrorCode.REQUIRED_SALARY, "USR_004");
        appCodes.put(UserErrorCode.INVALID_EMAIL, "USR_005");
        appCodes.put(UserErrorCode.INVALID_SALARY, "USR_006");
        appCodes.put(UserErrorCode.EMAIL_IN_USE, "USR_007");
        appCodes.put(UserErrorCode.USER_NOT_FOUND, "USR_008");
        appCodes.put(UserErrorCode.USER_NOT_FOUND_BY_EMAIL, "USR_008");

        Map<UserErrorCode, Integer> httpCodes = new EnumMap<>(UserErrorCode.class);
        httpCodes.put(UserErrorCode.REQUIRED_FIRSTNAME, 400);
        httpCodes.put(UserErrorCode.REQUIRED_LASTNAME, 400);
        httpCodes.put(UserErrorCode.REQUIRED_EMAIL, 400);
        httpCodes.put(UserErrorCode.REQUIRED_SALARY, 400);
        httpCodes.put(UserErrorCode.INVALID_EMAIL, 400);
        httpCodes.put(UserErrorCode.INVALID_SALARY, 400);
        httpCodes.put(UserErrorCode.EMAIL_IN_USE, 409);
        httpCodes.put(UserErrorCode.USER_NOT_FOUND, 404);
        httpCodes.put(UserErrorCode.USER_NOT_FOUND_BY_EMAIL, 404);

        Map<UserErrorCode, String> messages = new EnumMap<>(UserErrorCode.class);
        messages.put(UserErrorCode.REQUIRED_FIRSTNAME, "Firstname is required.");
        messages.put(UserErrorCode.REQUIRED_LASTNAME, "Lastname is required.");
        messages.put(UserErrorCode.REQUIRED_EMAIL, "Email is required.");
        messages.put(UserErrorCode.REQUIRED_SALARY, "Base salary is required.");
        messages.put(UserErrorCode.INVALID_EMAIL, "Invalid email format: {0}");
        messages.put(UserErrorCode.INVALID_SALARY, "Base salary must be between 0 and 15000000.");
        messages.put(UserErrorCode.EMAIL_IN_USE, "Email ''{0}'' is already in use.");
        messages.put(UserErrorCode.USER_NOT_FOUND, "User not found with id {0}");
        messages.put(UserErrorCode.USER_NOT_FOUND_BY_EMAIL, "User not found with email {0}");

        for (UserErrorCode c : UserErrorCode.values()) {
            assertThat(c.getAppCode()).isEqualTo(appCodes.get(c));
            assertThat(c.getHttpCode()).isEqualTo(httpCodes.get(c));
            assertThat(c.getMessage()).isEqualTo(messages.get(c));
            assertThat(c).isInstanceOf(ErrorCode.class);
        }
    }

    @Test
    @DisplayName("Message formating with {0} (MessageFormat)")
    void messageFormatting() {
        assertThat(MessageFormat.format(UserErrorCode.INVALID_EMAIL.getMessage(), "bad@mail"))
                .isEqualTo("Invalid email format: bad@mail");

        assertThat(MessageFormat.format(UserErrorCode.EMAIL_IN_USE.getMessage(), "john@mail.com"))
                .isEqualTo("Email 'john@mail.com' is already in use.");

        assertThat(MessageFormat.format(UserErrorCode.USER_NOT_FOUND.getMessage(), "123"))
                .isEqualTo("User not found with id 123");

        assertThat(MessageFormat.format(UserErrorCode.USER_NOT_FOUND_BY_EMAIL.getMessage(), "john@mail.com"))
                .isEqualTo("User not found with email john@mail.com");
    }

    @Test
    @DisplayName("values() contains the nine constants and valueOf() works")
    void valuesAndValueOf() {
        assertThat(UserErrorCode.values()).hasSize(9);
        assertThat(UserErrorCode.valueOf("REQUIRED_FIRSTNAME"))
                .isSameAs(UserErrorCode.REQUIRED_FIRSTNAME);
        assertThat(UserErrorCode.valueOf("USER_NOT_FOUND_BY_EMAIL"))
                .isSameAs(UserErrorCode.USER_NOT_FOUND_BY_EMAIL);
    }

}