package co.com.pragma.usecase.auth.contract;

import co.com.pragma.model.user.dto.request.LoginModelDTO;
import co.com.pragma.model.user.dto.response.JwtTokenDTO;
import reactor.core.publisher.Mono;

public interface AuthUseCaseContract {

    Mono<JwtTokenDTO> login(LoginModelDTO loginDTO);

}
