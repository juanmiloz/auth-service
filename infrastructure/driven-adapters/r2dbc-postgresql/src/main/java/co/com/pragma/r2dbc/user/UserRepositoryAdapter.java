package co.com.pragma.r2dbc.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.error.UserErrorCode;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.user.helper.UserDAOMapper;
import co.com.pragma.r2dbc.user.repository.UserDAORepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static co.com.pragma.model.shared.exception.DomainExceptionFactory.exceptionOf;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryAdapter implements UserRepository {

    private final UserDAORepository userRepository;
    private final UserDAOMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> createUser(User user) {
        return Mono.just(user)
                .map(mapper::toUserDAO)
                .map(userDAO -> {
                    String password = passwordEncoder.encode(userDAO.getPassword());
                    userDAO.setPassword(password);
                    return userDAO;
                })
                .doOnNext(dao -> log.info(">>> Saving entity: {}", dao))
                .flatMap(userRepository::save)
                .map(mapper::toUser);
    }

    @Override
    public Mono<User> getByEmail(String email) {
        return userRepository.findByEmail(email)
                .doOnSubscribe(s -> log.debug("Looking up user by email: {}", email))
                .map(mapper::toUser)
                .doOnNext(u -> log.info("User found for email {}", email))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("No user found for email {}", email);
                    return Mono.error(exceptionOf(UserErrorCode.USER_NOT_FOUND));
                }))
                .doOnError(e -> log.error("Error fetching user by email {}: {}", email, e.getMessage(), e));
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

}