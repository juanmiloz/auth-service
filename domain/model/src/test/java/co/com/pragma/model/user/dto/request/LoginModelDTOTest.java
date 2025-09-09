package co.com.pragma.model.user.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginModelDTOTest {

    @Test
    @DisplayName("Record expone campos y equals/hashCode por valor")
    void basics() {
        LoginModelDTO a = new LoginModelDTO("user@mail.com", "secret");
        LoginModelDTO b = new LoginModelDTO("user@mail.com", "secret");

        assertThat(a.email()).isEqualTo("user@mail.com");
        assertThat(a.password()).isEqualTo("secret");

        assertThat(a).isEqualTo(b);
        assertThat(a).hasSameHashCodeAs(b);
        assertThat(a.toString()).contains("user@mail.com");
    }

}