package co.com.pragma.api.user.data.request;

import java.util.UUID;

/**
 * Request payload for creating a user.
 *
 * @param firstName    user's first name
 * @param lastName     user's last name
 * @param email        user's email
 * @param id           user identifier (e.g., national ID)
 * @param phoneNumber  user's phone number
 * @param baseSalary   user's base salary
 */
public record CreateUserDTO(
        String firstName, String lastName, String email, String id, String phoneNumber, Double baseSalary, String password, UUID roleId
) {
}
