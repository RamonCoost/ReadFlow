package io.github.ramon.ReadFlow.business.dto.livro.request;

import jakarta.validation.constraints.PositiveOrZero;

public record AtualizaProgressoRequest(@PositiveOrZero
                                       int paginasLidas) {
}
