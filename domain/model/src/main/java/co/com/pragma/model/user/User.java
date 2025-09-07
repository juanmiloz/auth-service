package co.com.pragma.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Domain model representing a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private String phoneNumber;
    private Double baseSalary;
    private String password;
    private UUID roleId;

}
