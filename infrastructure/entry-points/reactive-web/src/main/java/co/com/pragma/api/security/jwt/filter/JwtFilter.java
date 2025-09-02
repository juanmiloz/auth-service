package co.com.pragma.api.security.jwt.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (path.contains("auth"))
            return chain.filter(exchange);
        String auth = request.getHeaders().getFirst("Authorization");
        if (auth != null) {
            String token = auth.replace("Bearer ", "");
            exchange.getAttributes().put("token", token);
        }
        return chain.filter(exchange);
    }
}
