package co.com.pragma.r2dbc.user.helper;

import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.user.data.UserDAO;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface UserDAOMapper {

    User toUser(UserDAO userDAO);

    UserDAO toUserDAO(User user);

    default Mono<User> toUser(Mono<UserDAO> userDAO) { return userDAO.map(this::toUser); }

    default Mono<UserDAO> toUserDAO(Mono<User> user) { return user.map(this::toUserDAO); }

    default Flux<User> toUser(Flux<UserDAO> userDAO) { return userDAO.map(this::toUser); }

    default Flux<UserDAO> toUserDAO(Flux<User> user) { return user.map(this::toUserDAO); }

}
