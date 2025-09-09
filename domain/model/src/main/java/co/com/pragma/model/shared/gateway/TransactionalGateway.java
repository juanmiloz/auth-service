package co.com.pragma.model.shared.gateway;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Abstraction for executing reactive actions in a transactional context.
 */
public interface TransactionalGateway {

    /**
     * Executes a reactive {@link Mono} within a transaction.
     */
    <T> Mono<T> execute(Mono<T> action);

    /**
     * Executes a reactive {@link Flux} within a transaction.
     */
    <T> Flux<T> execute(Flux<T> action);

}
