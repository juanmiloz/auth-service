package co.com.pragma.r2dbc.role;

import co.com.pragma.model.role.Role;
import co.com.pragma.model.role.error.RoleErrorCode;
import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.r2dbc.role.helper.RoleDAOMapper;
import co.com.pragma.r2dbc.role.repository.RoleDAORepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static co.com.pragma.model.shared.exception.DomainExceptionFactory.exceptionOf;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryAdapter implements RoleRepository {

    private final RoleDAORepository roleRepository;
    private final RoleDAOMapper mapper;

    @Override
    public Mono<Role> getRoleById(UUID roleId) {
        return roleRepository.findById(roleId)
                .switchIfEmpty(Mono.error(exceptionOf(RoleErrorCode.ROLE_NOT_FOUND)))
                .map(mapper::toRole);
    }
}
