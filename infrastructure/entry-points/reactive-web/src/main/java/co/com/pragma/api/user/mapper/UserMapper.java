package co.com.pragma.api.user.mapper;

import co.com.pragma.api.user.data.request.CreateUserDTO;
import co.com.pragma.api.user.data.response.UserDTO;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;

/**
 * Maps between user DTOs and domain entities.
 * <p>
 * Uses MapStruct to generate implementations.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a {@link CreateUserDTO} into a {@link User} entity.
     *
     * @param dto user creation request
     * @return mapped {@link User}
     */
    User toEntity(CreateUserDTO dto);


    /**
     * Converts a {@link User} entity into a {@link UserDTO}.
     *
     * @param user domain entity
     * @return mapped {@link UserDTO}
     */
    UserDTO toDTO(User user);

}
