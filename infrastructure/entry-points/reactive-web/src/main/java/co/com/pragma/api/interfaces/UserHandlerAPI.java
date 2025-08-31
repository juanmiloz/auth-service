package co.com.pragma.api.interfaces;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Contract for handling user-related requests.
 */
public interface UserHandlerAPI {

    /**
     * Handles user creation from an HTTP request.
     *
     * @param request incoming request with user data
     * @return {@link Mono} emitting {@link ServerResponse} with the result
     */
    Mono<ServerResponse> createUser(ServerRequest request);

}
