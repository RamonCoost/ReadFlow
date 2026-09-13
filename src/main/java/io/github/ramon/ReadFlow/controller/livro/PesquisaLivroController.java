package io.github.ramon.ReadFlow.controller.livro;

import io.github.ramon.ReadFlow.business.dto.livro.response.PesquisaLivroResponse;
import io.github.ramon.ReadFlow.business.service.livro.PesquisaLivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pesquisa")
@RequiredArgsConstructor
public class PesquisaLivroController {

    private final PesquisaLivroService pesquisaLivroService;

    @GetMapping
    ResponseEntity<List<PesquisaLivroResponse>> pesquisarLivros(@RequestParam String termo) {
        return ResponseEntity.ok(pesquisaLivroService.pesquisarLivro(termo));
    }
}
