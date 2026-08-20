package io.github.ramon.ReadFlow.business.service.auth;

import io.github.ramon.ReadFlow.business.dto.auth.request.LoginRequest;
import io.github.ramon.ReadFlow.business.dto.auth.response.TokenResponse;
import io.github.ramon.ReadFlow.business.service.security.JwtService;
import io.github.ramon.ReadFlow.infrastructure.security.UsuarioDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public TokenResponse loginUsuario(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken credenciais = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.senha());

        Authentication authentication = authenticationManager.authenticate(credenciais);

        UsuarioDetails usuarioDetails = (UsuarioDetails) authentication.getPrincipal();

        String token = jwtService.gerarToken(usuarioDetails.getUsuario());

        return new TokenResponse(token);
    }
}
