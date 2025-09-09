package co.com.pragma.model.role.error;

import co.com.pragma.model.shared.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

import static org.assertj.core.api.Assertions.assertThat;

class RoleErrorCodeTest {

    @Test
    @DisplayName("ROLE_NOT_FOUND expone metadatos correctos")
    void roleNotFound_metadata() {
        RoleErrorCode code = RoleErrorCode.ROLE_NOT_FOUND;

        assertThat(code.getAppCode()).isEqualTo("ROL_001");
        assertThat(code.getHttpCode()).isEqualTo(404);
        assertThat(code.getMessage()).isEqualTo("Role not found with id {0}");
    }

    @Test
    @DisplayName("Formato de mensaje con parámetro {0}")
    void messageFormatting() {
        String formatted = MessageFormat.format(RoleErrorCode.ROLE_NOT_FOUND.getMessage(), "123");
        assertThat(formatted).isEqualTo("Role not found with id 123");
    }

    @Test
    @DisplayName("values() y valueOf() funcionan y solo hay una constante")
    void valuesAndValueOf() {
        RoleErrorCode[] values = RoleErrorCode.values();
        assertThat(values).hasSize(1);
        assertThat(RoleErrorCode.valueOf("ROLE_NOT_FOUND"))
                .isSameAs(RoleErrorCode.ROLE_NOT_FOUND);
    }

    @Test
    @DisplayName("El enum implementa ErrorCode")
    void implementsErrorCode() {
        assertThat(RoleErrorCode.ROLE_NOT_FOUND).isInstanceOf(ErrorCode.class);
    }

}