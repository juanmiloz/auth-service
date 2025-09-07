package co.com.pragma.api.user.data.response;

public record AuthDTO(
         String accessToken,
         String idToken,
         String refreshToken,
         Integer expiresIn,
         String tokenType
) {
}
