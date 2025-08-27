package co.com.pragma.api;

import co.com.pragma.api.data.request.CreateUserDTO;
import co.com.pragma.api.interfaces.UserHandler;
import co.com.pragma.api.mapper.UserMapper;
import co.com.pragma.usecase.usercrud.UserCrudUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler implements UserHandler {

    private final UserCrudUseCase useCase;
    private final UserMapper mapper;

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

}
