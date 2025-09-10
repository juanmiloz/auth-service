package co.com.pragma.api;

import co.com.pragma.api.auth.api.AuthHandlerAPI;
import co.com.pragma.api.user.api.UserHandlerAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandlerAPI userHandler, AuthHandlerAPI authHandler) {
        return route(POST("/api/v1/users"), userHandler::createUser)
                .andRoute(POST("/api/v1/login"), authHandler::login)
                .andRoute(GET("/api/v1/users/email/{email:.+}"), userHandler::getUserByEmail);
    }
}