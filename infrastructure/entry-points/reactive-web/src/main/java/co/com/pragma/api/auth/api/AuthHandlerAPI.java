package co.com.pragma.api.auth.api;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface AuthHandlerAPI {

    Mono<ServerResponse> login(ServerRequest request);

}
