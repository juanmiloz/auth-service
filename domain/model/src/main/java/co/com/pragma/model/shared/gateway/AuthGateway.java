package co.com.pragma.model.shared.gateway;

import co.com.pragma.model.user.dto.response.JwtTokenDTO;
import co.com.pragma.model.user.dto.response.UserPrincipalDTO;
import reactor.core.publisher.Mono;

public interface AuthGateway {

    Boolean isPasswordMatch(String rawPassword, String encodedPassword);

    Mono<JwtTokenDTO> generateToken(UserPrincipalDTO user);
}
