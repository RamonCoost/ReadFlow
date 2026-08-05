package io.github.ramon.ReadFlow.controller;

import io.github.ramon.ReadFlow.business.dto.usuario.request.AtualizarUsuarioRequest;
import io.github.ramon.ReadFlow.business.dto.usuario.request.CadastroUsuarioRequest;
import io.github.ramon.ReadFlow.business.dto.usuario.response.UsuarioResponse;
import io.github.ramon.ReadFlow.business.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponse> salvarUsuario(@RequestBody @Valid CadastroUsuarioRequest cadastroUsuarioRequest){
        return ResponseEntity.ok(service.salvarUsuario(cadastroUsuarioRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid AtualizarUsuarioRequest atualizarUsuarioRequest){
        return ResponseEntity.ok(service.atualizarUsuario(id,atualizarUsuarioRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        service.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

}
