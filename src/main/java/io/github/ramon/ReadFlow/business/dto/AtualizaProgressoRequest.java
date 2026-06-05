package io.github.ramon.ReadFlow.business.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record AtualizaProgressoRequest(@PositiveOrZero
                                       int paginasLidas) {
}
