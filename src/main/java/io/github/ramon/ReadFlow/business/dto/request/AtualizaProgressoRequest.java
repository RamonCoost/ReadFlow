package io.github.ramon.ReadFlow.business.dto.request;

import jakarta.validation.constraints.PositiveOrZero;

public record AtualizaProgressoRequest(@PositiveOrZero
                                       int paginasLidas) {
}
