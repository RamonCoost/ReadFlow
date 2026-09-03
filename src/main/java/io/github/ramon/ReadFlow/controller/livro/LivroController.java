package io.github.ramon.ReadFlow.controller.livro;

import io.github.ramon.ReadFlow.business.dto.livro.request.AtualizaLivroRequest;
import io.github.ramon.ReadFlow.business.dto.livro.request.LivroRequest;
import io.github.ramon.ReadFlow.business.dto.livro.response.LivroResponse;
import io.github.ramon.ReadFlow.business.service.livro.LivroService;
import io.github.ramon.ReadFlow.infrastructure.enums.Status;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class LivroController {

    private final LivroService service;

    @PostMapping()
    public ResponseEntity<LivroResponse> salvarLivro(@RequestBody @Valid LivroRequest livroRequest) {
        return ResponseEntity.ok(service.salvarLivro(livroRequest));
    }

    @GetMapping
    public ResponseEntity<Page<LivroResponse>> listarLivros(Pageable pageable, @RequestParam(required = false)
                                                            Status status, @RequestParam(required = false) String pesquisa) {
        return ResponseEntity.ok(service.listarLivros(pageable, status, pesquisa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarLivroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarLivroPorId(id));
    }

    @GetMapping("/autor")
    public ResponseEntity<List<LivroResponse>> buscarLivroPorAutor(@RequestParam String autor) {
        return ResponseEntity.ok(service.buscarLivroPorAutor(autor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizarLivro(@PathVariable Long id, @Valid @RequestBody AtualizaLivroRequest livroRequest) {
        return ResponseEntity.ok(service.atualizarLivro(id, livroRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLivro(@PathVariable Long id) {
        service.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

}
