package co.com.pragma.usecase.auth;

import co.com.pragma.model.role.Role;
import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.model.shared.gateway.AuthGateway;
import co.com.pragma.model.shared.gateway.TransactionalGateway;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.request.LoginModelDTO;
import co.com.pragma.model.user.dto.response.JwtTokenDTO;
import co.com.pragma.model.user.dto.response.UserPrincipalDTO;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private TransactionalGateway transactionalGateway;
    @Mock
    private AuthGateway authGateway;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthUseCase useCase;

    // Utility: make TransactionalGateway return the inner Mono unchanged
    private void stubTransactionPassthrough() {
        when(transactionalGateway.execute(any(Mono.class)))
                .thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    @DisplayName("login(): success -> emits JwtTokenDTO and completes")
    void login_success() {
        // Arrange
        stubTransactionPassthrough();

        String email = "user@mail.com";
        String rawPassword = "raw";
        String storedPassword = "encoded";

        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        User user = User.builder()
                .userId(userId)
                .email(email)
                .password(storedPassword)
                .roleId(roleId)
                .build();

        Role role = Role.builder()
                .roleId(roleId)
                .name("ADMIN")
                .description("desc")
                .build();

        when(userRepository.getByEmail(email)).thenReturn(Mono.just(user));
        when(roleRepository.getRoleById(roleId)).thenReturn(Mono.just(role));
        when(authGateway.isPasswordMatch(rawPassword, storedPassword)).thenReturn(true);

        ArgumentCaptor<UserPrincipalDTO> principalCaptor = ArgumentCaptor.forClass(UserPrincipalDTO.class);
        when(authGateway.generateToken(any(UserPrincipalDTO.class)))
                .thenReturn(Mono.just(new JwtTokenDTO("jwt-token")));

        // Act
        Mono<JwtTokenDTO> result = useCase.login(new LoginModelDTO(email, rawPassword));

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(jwt -> "jwt-token".equals(jwt.token()))
                .verifyComplete();

        verify(userRepository, times(1)).getByEmail(email);
        verify(roleRepository, times(1)).getRoleById(roleId);
        verify(authGateway, times(1)).isPasswordMatch(rawPassword, storedPassword);
        verify(authGateway, times(1)).generateToken(principalCaptor.capture());
        verify(transactionalGateway, times(1)).execute(Mockito.<Mono<JwtTokenDTO>>any());

        UserPrincipalDTO captured = principalCaptor.getValue();
        assertThat(captured.email()).isEqualTo(email);
        assertThat(captured.userId()).isEqualTo(userId);
        assertThat(captured.password()).isEqualTo(storedPassword);
        assertThat(captured.roles()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("login(): user not found -> propagates error (INVALID_CREDENTIALS)")
    void login_userNotFound() {
        // Arrange
        stubTransactionPassthrough();

        String email = "absent@mail.com";
        String rawPassword = "x";

        when(userRepository.getByEmail(email)).thenReturn(Mono.empty());

        // Act
        Mono<JwtTokenDTO> result = useCase.login(new LoginModelDTO(email, rawPassword));

        // Assert
        StepVerifier.create(result)
                .expectErrorSatisfies(throwable -> assertThat(throwable).isNotNull())
                .verify();

        verify(userRepository, times(1)).getByEmail(email);
        verifyNoInteractions(roleRepository);
        verify(authGateway, never()).isPasswordMatch(anyString(), anyString());
        verify(authGateway, never()).generateToken(any());
        verify(transactionalGateway, times(1)).execute(any(Mono.class));
    }

    @Test
    @DisplayName("login(): wrong password -> propagates error (INVALID_CREDENTIALS)")
    void login_wrongPassword() {
        // Arrange
        stubTransactionPassthrough();

        String email = "user@mail.com";
        String rawPassword = "wrong";
        String storedPassword = "encoded";

        UUID roleId = UUID.randomUUID();
        User user = User.builder()
                .email(email)
                .password(storedPassword)
                .roleId(roleId)
                .build();

        when(userRepository.getByEmail(email)).thenReturn(Mono.just(user));
        when(roleRepository.getRoleById(roleId)).thenReturn(Mono.just(Role.builder()
                .roleId(roleId).name("USER").description("desc").build()));
        when(authGateway.isPasswordMatch(rawPassword, storedPassword)).thenReturn(false);

        // Act
        Mono<JwtTokenDTO> result = useCase.login(new LoginModelDTO(email, rawPassword));

        // Assert
        StepVerifier.create(result)
                .expectErrorSatisfies(throwable -> assertThat(throwable).isNotNull())
                .verify();

        verify(userRepository, times(1)).getByEmail(email);
        verify(roleRepository, times(1)).getRoleById(roleId);
        verify(authGateway, times(1)).isPasswordMatch(rawPassword, storedPassword);
        verify(authGateway, never()).generateToken(any());
        verify(transactionalGateway, times(1)).execute(any(Mono.class));
    }

    @Test
    @DisplayName("login(): token generation fails -> emits error from AuthGateway")
    void login_tokenGenerationFails() {
        // Arrange
        stubTransactionPassthrough();

        String email = "user@mail.com";
        String rawPassword = "ok";
        String storedPassword = "encoded";
        UUID roleId = UUID.randomUUID();

        User user = User.builder()
                .email(email)
                .password(storedPassword)
                .roleId(roleId)
                .build();

        when(userRepository.getByEmail(email)).thenReturn(Mono.just(user));
        when(roleRepository.getRoleById(roleId)).thenReturn(Mono.just(Role.builder()
                .roleId(roleId).name("USER").description("desc").build()));
        when(authGateway.isPasswordMatch(rawPassword, storedPassword)).thenReturn(true);
        when(authGateway.generateToken(any(UserPrincipalDTO.class)))
                .thenReturn(Mono.error(new IllegalStateException("token failure")));

        // Act
        Mono<JwtTokenDTO> result = useCase.login(new LoginModelDTO(email, rawPassword));

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(err -> err instanceof IllegalStateException
                        && "token failure".equals(err.getMessage()))
                .verify();

        verify(userRepository, times(1)).getByEmail(email);
        verify(roleRepository, times(1)).getRoleById(roleId);
        verify(authGateway, times(1)).isPasswordMatch(rawPassword, storedPassword);
        verify(authGateway, times(1)).generateToken(any(UserPrincipalDTO.class));
        verify(transactionalGateway, times(1)).execute(any(Mono.class));
    }

    @Test
    @DisplayName("buildUserPrincipalFromUser(): maps fields and role correctly")
    void buildUserPrincipalFromUser_mapsCorrectly() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        User user = User.builder()
                .userId(userId)
                .email("user@mail.com")
                .password("encoded")
                .roleId(roleId)
                .build();

        when(roleRepository.getRoleById(roleId))
                .thenReturn(Mono.just(Role.builder().roleId(roleId).name("MANAGER").description("d").build()));

        // Act
        Mono<UserPrincipalDTO> mono = useCase.buildUserPrincipalFromUser(user);

        // Assert
        StepVerifier.create(mono)
                .assertNext(up -> {
                    assertThat(up.userId()).isEqualTo(userId);
                    assertThat(up.email()).isEqualTo("user@mail.com");
                    assertThat(up.password()).isEqualTo("encoded");
                    assertThat(up.roles()).isEqualTo("MANAGER");
                })
                .verifyComplete();

        verify(roleRepository, times(1)).getRoleById(roleId);
    }
}