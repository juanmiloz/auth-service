package co.com.pragma.security;

import co.com.pragma.model.shared.gateway.AuthGateway;
import co.com.pragma.model.user.dto.response.JwtTokenDTO;
import co.com.pragma.model.user.dto.response.UserPrincipalDTO;
import co.com.pragma.security.jwt.provider.JwtProvider;
import co.com.pragma.security.mapper.UserPrincipalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthGatewayAdapter implements AuthGateway {

    private final PasswordEncoder passwordEncoder;
    private final UserPrincipalMapper mapper;
    private final JwtProvider jwtProvider;

    @Override
    public Boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public Mono<JwtTokenDTO> generateToken(UserPrincipalDTO user) {
        return Mono.just(
                new JwtTokenDTO(jwtProvider.generateToken(mapper.toUserPrincipal(user)))
        );
    }
}
