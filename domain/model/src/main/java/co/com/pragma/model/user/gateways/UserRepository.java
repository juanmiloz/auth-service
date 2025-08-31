package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * Reactive repository interface for user persistence operations.
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
     * Retrieves a user by its ID.
     *
     * @param userId unique identifier
     * @return {@link Mono} emitting the found user or empty if not found
     */
    Mono<User> getUserById(UUID userId);

    /**
     * Retrieves all users.
     *
     * @return {@link Flux} emitting all users
     */
    Flux<User> getAllUsers();

}
