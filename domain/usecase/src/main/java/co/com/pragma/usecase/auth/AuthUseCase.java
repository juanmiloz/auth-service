package co.com.pragma.usecase.auth;

import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.model.shared.gateway.AuthGateway;
import co.com.pragma.model.shared.gateway.TransactionalGateway;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.request.LoginModelDTO;
import co.com.pragma.model.user.dto.response.JwtTokenDTO;
import co.com.pragma.model.user.dto.response.UserPrincipalDTO;
import co.com.pragma.model.user.error.LoginErrorCode;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.auth.contract.AuthUseCaseContract;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.pragma.model.shared.exception.DomainExceptionFactory.exceptionOf;

@RequiredArgsConstructor
public class AuthUseCase implements AuthUseCaseContract {

    private final TransactionalGateway transactionalGateway;
    private final AuthGateway authGateway;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Mono<JwtTokenDTO> login(LoginModelDTO loginDTO) {
        return transactionalGateway.execute(
                userRepository.getByEmail(loginDTO.email())
                        .flatMap(this::buildUserPrincipalFromUser)
                        .filter(user -> authGateway.isPasswordMatch(loginDTO.password(), user.password()))
                        .flatMap(authGateway::generateToken)
                        .switchIfEmpty(Mono.error(exceptionOf(LoginErrorCode.INVALID_CREDENTIALS)))

        );
    }

    public Mono<UserPrincipalDTO> buildUserPrincipalFromUser(User user) {
        return roleRepository.getRoleById(user.getRoleId())
                .map(role -> {
                    System.out.println(role);
                    return UserPrincipalDTO.builder()
                                    .userId(user.getUserId())
                                    .email(user.getEmail())
                                    .password(user.getPassword())
                                    .roles(role.getName())
                                    .build();
                        }
                );
    }

}
