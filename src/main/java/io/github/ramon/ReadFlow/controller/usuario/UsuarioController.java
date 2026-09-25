package io.github.ramon.ReadFlow.controller.usuario;

import io.github.ramon.ReadFlow.business.dto.usuario.request.AtualizarUsuarioRequest;
import io.github.ramon.ReadFlow.business.dto.usuario.request.CadastroUsuarioRequest;
import io.github.ramon.ReadFlow.business.dto.usuario.response.UsuarioResponse;
import io.github.ramon.ReadFlow.business.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponse> salvarUsuario(@RequestBody @Valid CadastroUsuarioRequest cadastroUsuarioRequest) {
        return ResponseEntity.ok(service.salvarUsuario(cadastroUsuarioRequest));
    }

    @PutMapping()
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@RequestBody @Valid AtualizarUsuarioRequest atualizarUsuarioRequest) {
        return ResponseEntity.ok(service.atualizarUsuario(atualizarUsuarioRequest));
    }

    @PatchMapping("/email")
    public ResponseEntity<Void> confirmarEmail(@RequestParam String token){
        service.confirmarEmail(token);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deletarUsuario() {
        service.deletarUsuario();
        return ResponseEntity.noContent().build();
    }

}
