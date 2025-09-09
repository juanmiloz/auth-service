package co.com.pragma.api.auth.data.request;

public record LoginDTO(
        String email, String password
) {
}
