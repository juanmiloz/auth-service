package co.com.pragma.usecase.usercrud.interfaces;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Mono;

/**
 * Public contract for user CRUD use cases.
 */

public interface UserCrudUseCaseInterface {

    /**
     * Creates a new user after validation and normalization.
     *
     * @param user domain entity with input data
     * @return {@link Mono} emitting the created user
     */
    Mono<User> createUser(User user);

}
