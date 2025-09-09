package co.com.pragma.api.auth.mapper;

import co.com.pragma.api.auth.data.request.LoginDTO;
import co.com.pragma.api.auth.data.response.TokenDTO;
import co.com.pragma.model.user.dto.request.LoginModelDTO;
import co.com.pragma.model.user.dto.response.JwtTokenDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    LoginModelDTO toEntityDTO(LoginDTO loginDTO);

    TokenDTO toDTO(JwtTokenDTO jwtTokenDTO);



}
