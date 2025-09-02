package co.com.pragma.api.security.jwt.manager;

import co.com.pragma.api.security.jwt.provider.JwtProvider;
import co.com.pragma.model.shared.exception.DomainException;
import co.com.pragma.model.shared.exception.error.AuthenticationErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static co.com.pragma.model.shared.exception.DomainExceptionFactory.exceptionOf;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtProvider.getClaims(auth.getCredentials().toString()))
                .log()
                .onErrorResume(e -> Mono.error(getAuthError(e)))
                .map(claims -> new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        Stream.of(claims.get("roles"))
                                .map(role -> (List<Map<String, String>>) role)
                                .flatMap(role -> role.stream()
                                        .map(r -> r.get("authority"))
                                        .map(SimpleGrantedAuthority::new)
                                ).toList()
                ));
    }

    private DomainException getAuthError(Throwable e) {
        if (e instanceof io.jsonwebtoken.ExpiredJwtException) {
            return exceptionOf(AuthenticationErrorCode.EXPIRED_TOKEN);
        } else if (e instanceof io.jsonwebtoken.MalformedJwtException) {
            return exceptionOf(AuthenticationErrorCode.MALFORMED_TOKEN);
        } else if (e instanceof io.jsonwebtoken.UnsupportedJwtException) {
            return exceptionOf(AuthenticationErrorCode.UNSUPPORTED_TOKEN);
        } else if (e instanceof io.jsonwebtoken.security.SignatureException) {
            return exceptionOf(AuthenticationErrorCode.BAD_SIGNATURE);
        } else if (e instanceof IllegalArgumentException) {
            return exceptionOf(AuthenticationErrorCode.ILLEGAL_ARGUMENT);
        }
        return null;
    }
}
