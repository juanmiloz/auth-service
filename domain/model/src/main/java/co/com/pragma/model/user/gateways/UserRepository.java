package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.dto.request.LoginModelDTO;
import co.com.pragma.model.user.dto.response.JwtTokenDTO;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.dto.response.UserPrincipalDTO;
import reactor.core.publisher.Mono;

/**
 * Reactive repository interface for user persistence and authentication operations.
 * <p>
 * All methods are non-blocking and return a {@link Mono} that either emits the result
 * or completes with an error. Implementations should avoid blocking I/O.
 * </p>
 */
public interface UserRepository {

    /**
     * Persists a new user.
     *
     * @param user domain entity
     * @return {@link Mono} emitting the created user
     */
    Mono<User> createUser(User user);

    /**
     * Checks if a user exists by email.
     *
     * @param email user's email
     * @return {@link Mono} emitting true if the user exists, false otherwise
     */
    Mono<Boolean> existsByEmail(String email);

    /**
     * Retrieves a user by email.
     *
     * @param email user's email; must not be {@code null} or blank
     * @return {@link Mono} emitting the {@link User} if found, or {@link Mono#empty()} if not found
     */
    Mono<User> getByEmail(String email);

}
