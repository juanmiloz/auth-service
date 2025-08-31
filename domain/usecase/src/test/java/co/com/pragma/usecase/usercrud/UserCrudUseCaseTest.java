package co.com.pragma.usecase.usercrud;

import co.com.pragma.model.shared.exception.DomainException;
import co.com.pragma.model.shared.gateway.TransactionalGateway;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.usercrud.util.UserCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class UserCrudUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionalGateway transactionalGateway;

    @InjectMocks
    private UserCrudUseCase userCrudUseCase;

    @BeforeAll
    static void blockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    void setup() {
        when(transactionalGateway.execute(any(Mono.class)))
                .thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(1_0);
                return "";
            });

            Schedulers.parallel().schedule(task);

            task.get(10, TimeUnit.SECONDS);

            Assertions.fail("should fail");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

    @Test
    @DisplayName("createUser: success case normalizes and persists")
    void createUser_success() {
        User input = UserCreator.createValidUser();

        when(userRepository.existsByEmail("juan.perez@mail.com"))
                .thenReturn(Mono.just(false));
        when(userRepository.createUser(any(User.class)))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(userCrudUseCase.createUser(input))
                .expectNextMatches(u ->
                        u.getFirstName().equals("Juan") &&
                                u.getLastName().equals("Perez") &&
                                u.getEmail().equals("juan.perez@mail.com") &&
                                u.getId().equals("123") &&
                                u.getPhoneNumber().equals("555-1234") &&
                                u.getBaseSalary().equals(5_000_000d)
                )
                .verifyComplete();

        verify(userRepository).existsByEmail("juan.perez@mail.com");
        verify(userRepository).createUser(any(User.class));
    }

    @Test
    @DisplayName("duplicate email -> DomainException")
    void duplicatedEmail() {
        User input = UserCreator.createUserWithDuplicatedEmail();

        when(userRepository.existsByEmail("duplicate@mail.com"))
                .thenReturn(Mono.just(true));

        StepVerifier.create(userCrudUseCase.createUser(input))
                .expectError(DomainException.class)
                .verify();

        verify(userRepository).existsByEmail("duplicate@mail.com");
        verify(userRepository, never()).createUser(any());
    }

    @Test
    @DisplayName("normalize: safeTrim and safeLowerTrim should clean spaces and lowercase email")
    void normalize_safeTrimAndSafeLowerTrim() {
        User input = UserCreator.createUserWithSpacesAndUppercaseEmail();

        when(userRepository.existsByEmail("test@mail.com"))
                .thenReturn(Mono.just(false));
        when(userRepository.createUser(any(User.class)))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(userCrudUseCase.createUser(input))
                .expectNextMatches(u ->
                        u.getFirstName().equals("Juan") &&
                                u.getLastName().equals("Perez") &&
                                u.getEmail().equals("test@mail.com") &&
                                u.getId().equals("123") &&
                                u.getPhoneNumber().equals("555-1234")
                )
                .verifyComplete();
    }

    @Nested
    class ValidationErrors {

        @Test
        @DisplayName("blank firstname -> DomainException")
        void blankFirstName() {
            User input = UserCreator.createUserWithBlankFirstName();

            StepVerifier.create(userCrudUseCase.createUser(input))
                    .expectError(DomainException.class)
                    .verify();

            verify(userRepository, never()).existsByEmail(anyString());
            verify(userRepository, never()).createUser(any());
        }

        @Test
        @DisplayName("blank lastname -> DomainException")
        void blankLastName() {
            User input = UserCreator.createUserWithBlankLastName();

            StepVerifier.create(userCrudUseCase.createUser(input))
                    .expectError(DomainException.class)
                    .verify();

            verify(userRepository, never()).existsByEmail(anyString());
            verify(userRepository, never()).createUser(any());
        }

        @Test
        @DisplayName("blank email -> DomainException")
        void blankEmail() {
            User input = UserCreator.createUserWithBlankEmail();

            StepVerifier.create(userCrudUseCase.createUser(input))
                    .expectError(DomainException.class)
                    .verify();

            verify(userRepository, never()).existsByEmail(anyString());
            verify(userRepository, never()).createUser(any());
        }

        @Test
        @DisplayName("invalid email -> DomainException")
        void invalidEmail() {
            User input = UserCreator.createUserWithInvalidEmail();

            StepVerifier.create(userCrudUseCase.createUser(input))
                    .expectError(DomainException.class)
                    .verify();

            verify(userRepository, never()).existsByEmail(anyString());
            verify(userRepository, never()).createUser(any());
        }

        @Test
        @DisplayName("null salary -> DomainException")
        void nullSalary() {
            User input = UserCreator.createUserWithNullSalary();

            StepVerifier.create(userCrudUseCase.createUser(input))
                    .expectError(DomainException.class)
                    .verify();

            verify(userRepository, never()).existsByEmail(anyString());
            verify(userRepository, never()).createUser(any());
        }

        @Test
        @DisplayName("negative salary -> DomainException")
        void negativeSalary() {
            User input = UserCreator.createUserWithNegativeSalary();

            StepVerifier.create(userCrudUseCase.createUser(input))
                    .expectError(DomainException.class)
                    .verify();

            verify(userRepository, never()).existsByEmail(anyString());
            verify(userRepository, never()).createUser(any());
        }

        @Test
        @DisplayName("too high salary -> DomainException")
        void tooHighSalary() {
            User input = UserCreator.createUserWithTooHighSalary();

            StepVerifier.create(userCrudUseCase.createUser(input))
                    .expectError(DomainException.class)
                    .verify();

            verify(userRepository, never()).existsByEmail(anyString());
            verify(userRepository, never()).createUser(any());
        }
    }

}
