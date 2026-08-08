package io.github.ramon.ReadFlow.controller.auth;

import io.github.ramon.ReadFlow.business.dto.auth.request.LoginRequest;
import io.github.ramon.ReadFlow.business.dto.auth.response.TokenResponse;
import io.github.ramon.ReadFlow.business.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUsuario(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.loginUsuario(loginRequest));
    }

}
