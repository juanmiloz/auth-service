package co.com.pragma.model.user.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenDTOTest {

    @Test
    @DisplayName("Record expone token y equals/hashCode por valor")
    void basics() {
        JwtTokenDTO a = new JwtTokenDTO("jwt-token");
        JwtTokenDTO b = new JwtTokenDTO("jwt-token");

        assertThat(a.token()).isEqualTo("jwt-token");
        assertThat(a).isEqualTo(b);
        assertThat(a).hasSameHashCodeAs(b);
        assertThat(a.toString()).contains("jwt-token");
    }

}