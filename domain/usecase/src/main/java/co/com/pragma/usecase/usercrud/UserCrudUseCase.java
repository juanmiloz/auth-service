package co.com.pragma.usecase.usercrud;

import co.com.pragma.model.shared.exception.DomainExceptionFactory;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exception.error.UserErrorCode;
import co.com.pragma.model.user.gateways.TransactionalGateway;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class UserCrudUseCase {

    private final UserRepository userRepository;
    private final TransactionalGateway transactionalGateway;
    private static final Pattern EMAIL_RX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public Mono<User> createUser(User user) {
        return transactionalGateway.execute(
                validateUserFields(user)
                        .map(this::normalize)
                        .flatMap(this::ensureEmailNotTaken)
                        .flatMap(userRepository::createUser)
        );
    }

    private Mono<User> validateUserFields(User user) {
        return Mono.fromCallable(() -> {
                    requireNotBlank(user.getFirstName(), UserErrorCode.REQUIRED_FIRSTNAME);
                    requireNotBlank(user.getLastName(), UserErrorCode.REQUIRED_LASTNAME);
                    requireNotBlank(user.getEmail(), UserErrorCode.REQUIRED_EMAIL);
                    requireNotNull(user.getBaseSalary(), UserErrorCode.REQUIRED_SALARY);
                    validateEmailFormat(user.getEmail());
                    validateSalary(user.getBaseSalary());
                    return user;
                }
        );
    }

    private void requireNotBlank(String value, UserErrorCode errorCode) {
        if (value == null || value.trim().isBlank()) {
            throw DomainExceptionFactory.exceptionOf(errorCode);
        }
    }

    private void requireNotNull(Object value, UserErrorCode errorCode) {
        if (Objects.isNull(value)) {
            throw DomainExceptionFactory.exceptionOf(errorCode);
        }
    }

    private void validateEmailFormat(String email) {
        if (!EMAIL_RX.matcher(email).matches()) {
            throw DomainExceptionFactory.exceptionOf(UserErrorCode.INVALID_EMAIL, email);
        }
    }

    private void validateSalary(Double salary) {
        if (salary < 0 || salary > 15_000_000) {
            throw DomainExceptionFactory.exceptionOf(UserErrorCode.INVALID_SALARY);
        }
    }

    private Mono<User> ensureEmailNotTaken(User user) {
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> exists
                        ? Mono.error(DomainExceptionFactory.exceptionOf(UserErrorCode.EMAIL_IN_USE, user.getEmail()))
                        : Mono.just(user)
                );
    }

    private User normalize(User u) {
        return new User(
                u.getUserId(),
                safeTrim(u.getFirstName()),
                safeTrim(u.getLastName()),
                safeLowerTrim(u.getEmail()),
                safeTrim(u.getId()),
                safeTrim(u.getPhoneNumber()),
                u.getBaseSalary()
        );
    }

    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

    private String safeLowerTrim(String s) {
        return s == null ? null : s.trim().toLowerCase();
    }

}
