package io.github.ramon.ReadFlow.business.dto;

import io.github.ramon.ReadFlow.infrastructure.enums.Status;

public record LivroRequest(String titulo,

                           String autor,

                           int totalPaginas,

                           Status statusLeitura) {
}
