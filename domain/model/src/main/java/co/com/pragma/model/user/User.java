package co.com.pragma.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private String phoneNumber;
    private Double baseSalary;

}
