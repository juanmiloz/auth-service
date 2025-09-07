package co.com.pragma.api.user;

import co.com.pragma.api.user.data.request.CreateUserDTO;
import co.com.pragma.api.user.api.UserHandlerAPI;
import co.com.pragma.api.user.mapper.UserMapper;
import co.com.pragma.usecase.usercrud.UserCrudUseCase;
import co.com.pragma.usecase.usercrud.contract.UserCrudUseCaseContract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Reactive request handler for user operations.
 * <p>
 * Implements {@link UserHandlerAPI} and delegates business logic to
 * {@link UserCrudUseCase} and mapping to {@link UserMapper}.
 * Uses Spring WebFlux {@link ServerRequest}/{@link ServerResponse}.
 * </p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserHandler implements UserHandlerAPI {

    private final UserCrudUseCaseContract useCase;
    private final UserMapper mapper;

    /**
     * Handles user creation.
     *
     * Reads the request body as {@link CreateUserDTO}, maps it to an entity,
     * delegates persistence to {@link UserCrudUseCase}, and returns a 201 response
     * with the created user DTO.
     *
     * @param request HTTP request with user data
     * @return 201 {@link ServerResponse} containing the created user
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','ADVISOR')")
    @Override
    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request
                .bodyToMono(CreateUserDTO.class)
                .doOnSubscribe(s -> log.debug("createUser:reading-body"))
                .doOnNext(dto -> log.debug("createUser:payload-received dto=[masked]"))
                .map(mapper::toEntity)
                .doOnNext(entity -> log.debug("createUser:mapped-to-entity entityClass={}", entity.getClass().getSimpleName()))
                .flatMap(useCase::createUser)
                .doOnNext(created -> log.info("createUser:usecase-success userId={}", created.getId()))
                .map(mapper::toDTO)
                .flatMap(res -> ServerResponse.created(URI.create("/api/v1/users/" + res.id())).bodyValue(res))
                .doOnError(ex -> log.error("createUser:error msg={}", ex.getMessage(), ex));
    }

    @Override
    public Mono<ServerResponse> getUserByEmail(ServerRequest request) {
        final String email = request.pathVariable("email");
        return useCase.getUserByEmail(email)
                .doOnSubscribe(s -> log.debug("getUserByEmail:start email={}", email))
                .doOnNext(u -> log.info("getUserByEmail:usecase-success userId={} email={}", u.getId(), email))
                .map(mapper::toDTO)
                .doOnNext(dto -> log.debug("getUserByEmail:mapped-to-dto userId={}", dto.id()))
                .flatMap(dto -> ServerResponse.ok().bodyValue(dto))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("getUserByEmail:not-found email={}", email);
                    return ServerResponse.notFound().build();
                }))
                .doOnError(ex -> log.error("getUserByEmail:error email={} msg={}", email, ex.getMessage(), ex));
    }
}