package co.com.pragma.api.user.data.response;

import java.util.UUID;

/**
 * Response payload representing a user.
 *
 * @param userId       unique identifier of the user
 * @param firstName    user's first name
 * @param lastName     user's last name
 * @param email        user's email
 * @param id           user identifier (e.g., national ID)
 * @param phoneNumber  user's phone number
 * @param baseSalary   user's base salary
 */
public record UserDTO(
        UUID userId, String firstName, String lastName, String email, String id, String phoneNumber, Double baseSalary, String password, String roleId
) {
}
