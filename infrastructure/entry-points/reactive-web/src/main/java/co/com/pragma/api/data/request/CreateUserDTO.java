package co.com.pragma.api.data.request;

public record CreateUserDTO(
        String firstName, String lastName, String email, String id, String phoneNumber, Double baseSalary
) {
}
