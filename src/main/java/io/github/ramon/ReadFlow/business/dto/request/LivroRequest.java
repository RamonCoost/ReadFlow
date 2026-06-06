package io.github.ramon.ReadFlow.business.dto.request;

import io.github.ramon.ReadFlow.infrastructure.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;


public record LivroRequest(@NotBlank
                           String titulo,

                           @NotBlank
                           String autor,

                           @Positive
                           int totalPaginas,

                           @PositiveOrZero
                           int paginasLidas) {
}
