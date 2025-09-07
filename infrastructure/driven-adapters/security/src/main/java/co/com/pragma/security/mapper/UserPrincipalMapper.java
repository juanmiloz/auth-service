package co.com.pragma.security.mapper;

import co.com.pragma.model.user.dto.response.UserPrincipalDTO;
import co.com.pragma.security.data.UserPrincipal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPrincipalMapper {

    UserPrincipal toUserPrincipal(UserPrincipalDTO userPrincipal);

}
