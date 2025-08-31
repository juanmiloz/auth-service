package co.com.pragma.r2dbc;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.helper.UserDAOMapper;
import co.com.pragma.r2dbc.repository.UserDAORepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryAdapter implements UserRepository {

    private final UserDAORepository userRepository;
    private final UserDAOMapper mapper;

    @Override
    public Mono<User> createUser(User user) {
        return Mono.just(user)
                .map(mapper::toUserDAO)
                .doOnNext(dao -> log.info(">>> Saving entity: {}", dao))
                .flatMap(userRepository::save)
                .map(mapper::toUser);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userRepository.existsByEmail(email)
                .doOnSuccess(exists -> {
                            if(exists) log.info("existsByEmail:email={} already exists", email);
                            else  log.info("existsByEmail:email={} not found", email);
                        }
                )
                .doOnError(ex -> log.error("existsByEmail:error email={} msg={}", email, ex.getMessage(), ex));
    }

    @Override
    public Mono<User> getUserById(UUID userId) {
        return null;
    }

    @Override
    public Flux<User> getAllUsers() {
        return null;
    }
}