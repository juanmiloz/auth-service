package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    Mono<User> createUser(User user);

    Mono<Boolean> existsByEmail(String email);

    Mono<User> getUserById(UUID userId);

    Flux<User> getAllUsers();

}
