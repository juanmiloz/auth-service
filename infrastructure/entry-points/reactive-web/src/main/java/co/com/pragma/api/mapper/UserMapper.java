package co.com.pragma.api.mapper;

import co.com.pragma.api.data.request.CreateUserDTO;
import co.com.pragma.api.data.response.UserDTO;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserDTO dto);
    UserDTO toDTO(User user);

}
