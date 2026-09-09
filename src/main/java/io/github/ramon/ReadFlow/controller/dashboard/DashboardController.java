package io.github.ramon.ReadFlow.controller.dashboard;

import io.github.ramon.ReadFlow.business.dto.dashboard.ResumoDashboardResponse;
import io.github.ramon.ReadFlow.business.service.livro.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final LivroService service;

    @GetMapping("/resumo")
    public ResponseEntity<ResumoDashboardResponse> buscarResumo() {
        return ResponseEntity.ok(service.buscarResumo());
    }
}
