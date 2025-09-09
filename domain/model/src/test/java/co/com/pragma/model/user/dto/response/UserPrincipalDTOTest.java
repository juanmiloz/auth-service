package co.com.pragma.model.user.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserPrincipalDTOTest {

    @Test
    @DisplayName("@Builder crea y toBuilder permite modificar")
    void builderAndToBuilder() {
        UUID id = UUID.randomUUID();

        UserPrincipalDTO principal = UserPrincipalDTO.builder()
                .userId(id)
                .email("user@mail.com")
                .password("encoded")
                .roles("ROLE_USER")
                .build();

        assertThat(principal.userId()).isEqualTo(id);
        assertThat(principal.email()).isEqualTo("user@mail.com");
        assertThat(principal.password()).isEqualTo("encoded");
        assertThat(principal.roles()).isEqualTo("ROLE_USER");

        UserPrincipalDTO changed = principal.toBuilder().roles("ROLE_ADMIN").build();
        assertThat(changed.roles()).isEqualTo("ROLE_ADMIN");
        assertThat(changed.userId()).isEqualTo(principal.userId());
        assertThat(changed.email()).isEqualTo(principal.email());
        assertThat(changed).isNotSameAs(principal);
    }

}