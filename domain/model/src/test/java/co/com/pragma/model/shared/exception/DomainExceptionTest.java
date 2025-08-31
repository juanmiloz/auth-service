package co.com.pragma.model.shared.exception;

import co.com.pragma.model.shared.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DomainExceptionTest {

    enum DummyCode implements ErrorCode {
        DUMMY("APP_000", 418, "I am a teapot");
        private final String appCode;
        private final int http;
        private final String message;
        DummyCode(String appCode, int http, String message) {
            this.appCode = appCode; this.http = http; this.message = message;
        }
        public String getAppCode() { return appCode; }
        public int getHttpCode() { return http; }
        public String getMessage() { return message; }
    }

    @Test
    @DisplayName("getters delegan correctamente al ErrorCode")
    void getters_delegate() {
        DomainException ex = new DomainException(DummyCode.DUMMY, "detalle");

        assertThat(ex.getErrorCode()).isEqualTo(DummyCode.DUMMY);
        assertThat(ex.getAppCode()).isEqualTo("APP_000");
        assertThat(ex.getHttpCode()).isEqualTo(418);
        assertThat(ex.getMessageTemplate()).isEqualTo("I am a teapot");
        assertThat(ex.getMessage()).isEqualTo("detalle");
    }

}