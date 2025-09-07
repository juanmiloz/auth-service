package co.com.pragma.r2dbc.role.repository;

import co.com.pragma.r2dbc.role.data.RoleDAO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.util.UUID;

/**
 * Repository interface for managing {@link RoleDAO} entities in the database.
 * <p>
 * Extends {@link R2dbcRepository} to provide reactive CRUD operations for roles.
 * </p>
 */
public interface RoleDAORepository extends R2dbcRepository<RoleDAO, UUID> {
}
