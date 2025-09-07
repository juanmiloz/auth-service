package co.com.pragma.usecase.usercrud.contract;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Mono;

/**
 * Public contract for user CRUD use cases.
 */
public interface UserCrudUseCaseContract {

    /**
     * Creates a new user after validation and normalization.
     *
     * @param user domain entity with input data
     * @return {@link Mono} emitting the created user
     */
    Mono<User> createUser(User user);

    /**
     * Retrieves a user by email.
     *
     * @param email email address to search; must not be {@code null} or blank
     * @return {@link Mono} emitting the found user
     */
    Mono<User> getUserByEmail(String email);

}
