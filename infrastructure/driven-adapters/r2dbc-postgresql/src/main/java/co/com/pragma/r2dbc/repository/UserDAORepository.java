package co.com.pragma.r2dbc.repository;

import co.com.pragma.r2dbc.data.UserDAO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserDAORepository extends R2dbcRepository<UserDAO, UUID> {

    Mono<Boolean> existsByEmail(String email);

}
