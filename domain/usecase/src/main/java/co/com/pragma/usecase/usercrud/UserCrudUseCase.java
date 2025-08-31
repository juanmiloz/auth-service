package co.com.pragma.usecase.usercrud;

import co.com.pragma.model.shared.exception.DomainExceptionFactory;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.error.UserErrorCode;
import co.com.pragma.model.shared.gateway.TransactionalGateway;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.usercrud.interfaces.UserCrudUseCaseInterface;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Implementation of {@link UserCrudUseCaseInterface}.
 * <p>
 * Validates, normalizes and persists user entities.
 * </p>
 */
@RequiredArgsConstructor
public class UserCrudUseCase implements UserCrudUseCaseInterface {

    private final UserRepository userRepository;
    private final TransactionalGateway transactionalGateway;
    private static final Pattern EMAIL_RX = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public Mono<User> createUser(User user) {
        return transactionalGateway.execute(
                validateUserFields(user)
                        .map(this::normalize)
                        .flatMap(this::ensureEmailNotTaken)
                        .flatMap(userRepository::createUser)
        );
    }

    /**
     * Validates mandatory fields of the user entity:
     * <ul>
     *     <li>Firstname and lastname must not be blank.</li>
     *     <li>Email must not be blank and must follow a valid format.</li>
     *     <li>Base salary must not be null and within allowed range.</li>
     * </ul>
     * If any rule fails, a {@link co.com.pragma.model.shared.exception.DomainException} is thrown.
     *
     * @param user user entity to validate
     * @return {@link Mono} emitting the same user if valid
     */
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

    /**
     * Ensures that a string field is not null or blank.
     *
     * @param value     field value to check
     * @param errorCode error code to throw if invalid
     * @throws co.com.pragma.model.shared.exception.DomainException if value is null/blank
     */
    private void requireNotBlank(String value, UserErrorCode errorCode) {
        if (value == null || value.trim().isBlank()) {
            throw DomainExceptionFactory.exceptionOf(errorCode);
        }
    }

    /**
     * Ensures that an object is not null.
     *
     * @param value     field value to check
     * @param errorCode error code to throw if null
     * @throws co.com.pragma.model.shared.exception.DomainException if value is null
     */
    private void requireNotNull(Object value, UserErrorCode errorCode) {
        if (Objects.isNull(value)) {
            throw DomainExceptionFactory.exceptionOf(errorCode);
        }
    }

    /**
     * Validates email format using {@link #EMAIL_RX}.
     *
     * @param email email string to validate
     * @throws co.com.pragma.model.shared.exception.DomainException if email does not match regex
     */
    private void validateEmailFormat(String email) {
        if (!EMAIL_RX.matcher(email).matches()) {
            throw DomainExceptionFactory.exceptionOf(UserErrorCode.INVALID_EMAIL, email);
        }
    }

    /**
     * Validates that the salary is within the allowed range [0, 15,000,000].
     *
     * @param salary salary to validate
     * @throws co.com.pragma.model.shared.exception.DomainException if salary is out of range
     */
    private void validateSalary(Double salary) {
        if (salary < 0 || salary > 15_000_000) {
            throw DomainExceptionFactory.exceptionOf(UserErrorCode.INVALID_SALARY);
        }
    }

    /**
     * Verifies that the user's email is not already taken.
     *
     * @param user user entity to check
     * @return {@link Mono} emitting the same user if email is available,
     *         or error if already in use
     */

    private Mono<User> ensureEmailNotTaken(User user) {
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> exists
                        ? Mono.error(DomainExceptionFactory.exceptionOf(UserErrorCode.EMAIL_IN_USE, user.getEmail()))
                        : Mono.just(user)
                );
    }

    /**
     * Normalizes user fields:
     * <ul>
     *     <li>Trims whitespace from names, IDs and phone number.</li>
     *     <li>Lowercases and trims the email.</li>
     * </ul>
     *
     * @param u user to normalize
     * @return new {@link User} instance with normalized fields
     */
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

    /**
     * Null-safe trim operation.
     *
     * @param s input string
     * @return trimmed string or null if input is null
     */
    private String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

    /**
     * Null-safe trim and lowercase operation.
     *
     * @param s input string
     * @return trimmed and lowercased string or null if input is null
     */
    private String safeLowerTrim(String s) {
        return s == null ? null : s.trim().toLowerCase();
    }

}
