package co.com.pragma.r2dbc.repository;

import co.com.pragma.r2dbc.data.UserDAO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Reactive R2DBC repository for {@link UserDAO}.
 * <p>
 * Provides CRUD operations and custom queries for users.
 * </p>
 */
public interface UserDAORepository extends R2dbcRepository<UserDAO, UUID> {

    /**
     * Checks if a user exists with the given email.
     *
     * @param email user email
     * @return {@link Mono} emitting true if exists, false otherwise
     */
    Mono<Boolean> existsByEmail(String email);

}
