package co.com.pragma.model.user.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record UserPrincipalDTO(
        UUID userId,
        String email,
        String password,
        String roles
) {
}
