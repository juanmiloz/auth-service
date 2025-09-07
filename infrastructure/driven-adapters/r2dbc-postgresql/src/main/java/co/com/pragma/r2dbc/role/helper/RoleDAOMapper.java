package co.com.pragma.r2dbc.role.helper;

import co.com.pragma.model.role.Role;
import co.com.pragma.r2dbc.role.data.RoleDAO;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Mapper interface for converting between {@link RoleDAO} and {@link Role} entities.
 * <p>
 * Provides methods to map single objects and reactive streams of roles and role DAOs.
 * Utilizes MapStruct for automatic implementation and supports reactive types with Project Reactor.
 * </p>
 *
 * <p>
 * Annotated with {@code @Mapper(componentModel = "spring")} for Spring integration.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface RoleDAOMapper{

    /**
     * Converts a {@link RoleDAO} to a {@link Role}.
     *
     * @param roleDAO the role DAO entity
     * @return the domain role entity
     */
    Role toRole(RoleDAO roleDAO);

    /**
     * Converts a {@link Role} to a {@link RoleDAO}.
     *
     * @param role the domain role entity
     * @return the role DAO entity
     */
    RoleDAO toRoleDAO(Role role);

    default Mono<Role> toRole(Mono<RoleDAO> roleDAO) { return roleDAO.map(this::toRole); }

    default Mono<RoleDAO> toRoleDAO(Mono<Role> role) { return role.map(this::toRoleDAO); }

    default Flux<Role> toRole(Flux<RoleDAO> roleDAO) { return roleDAO.map(this::toRole); }

    default Flux<RoleDAO> toRoleDAO(Flux<Role> role) { return role.map(this::toRoleDAO); }

}
