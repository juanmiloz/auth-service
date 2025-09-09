package co.com.pragma.api.auth;

import co.com.pragma.api.auth.api.AuthHandlerAPI;
import co.com.pragma.api.auth.data.request.LoginDTO;
import co.com.pragma.api.auth.mapper.AuthMapper;
import co.com.pragma.usecase.auth.contract.AuthUseCaseContract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthHandler implements AuthHandlerAPI {

    private final AuthUseCaseContract useCase;
    private final AuthMapper mapper;

    @Override
    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginDTO.class)
                .map(mapper::toEntityDTO)
                .flatMap(useCase::login)
                .map(mapper::toDTO)
                .flatMap(res -> ServerResponse.ok().bodyValue(res));
    }
}
