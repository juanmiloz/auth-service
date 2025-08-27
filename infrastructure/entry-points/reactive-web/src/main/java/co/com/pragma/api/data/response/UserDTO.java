package co.com.pragma.api.data.response;

import java.util.UUID;

public record UserDTO(
        UUID userId, String firstName, String lastName, String email, String id, String phoneNumber, Double baseSalary
) {
}
