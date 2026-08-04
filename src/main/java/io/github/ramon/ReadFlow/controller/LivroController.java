package io.github.ramon.ReadFlow.controller;

import io.github.ramon.ReadFlow.business.dto.request.AtualizaLivroRequest;
import io.github.ramon.ReadFlow.business.dto.request.AtualizaProgressoRequest;
import io.github.ramon.ReadFlow.business.dto.request.AtualizaStatusRequest;
import io.github.ramon.ReadFlow.business.dto.request.LivroRequest;
import io.github.ramon.ReadFlow.business.dto.response.LivroResponse;
import io.github.ramon.ReadFlow.business.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<LivroResponse> salvarLivro(@RequestBody @Valid LivroRequest livroRequest) {
        return ResponseEntity.ok(service.salvarLivro(livroRequest));
    }

    @GetMapping
    public ResponseEntity<List<LivroResponse>> listarLivros() {
        return ResponseEntity.ok(service.listarLivros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarLivroPorId( @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarLivroPorId(id));
    }

    @GetMapping("/autor")
    public ResponseEntity<List<LivroResponse>> buscarLivroPorAutor(@RequestParam String autor) {
        return ResponseEntity.ok(service.buscarLivroPorAutor(autor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizarLivro(@PathVariable Long id, @Valid @RequestBody AtualizaLivroRequest livroRequest){
        return ResponseEntity.ok(service.atualizarLivro(id, livroRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LivroResponse> excluirLivro(@PathVariable Long id) {
        service.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

}
