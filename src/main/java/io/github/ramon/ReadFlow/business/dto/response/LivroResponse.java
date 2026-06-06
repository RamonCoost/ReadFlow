package io.github.ramon.ReadFlow.business.dto.response;

import io.github.ramon.ReadFlow.infrastructure.enums.Status;

public record LivroResponse(Long id,

                            String titulo,

                            String autor,

                            int totalPaginas,

                            int paginasLidas,

                            Status statusLeitura) {
}
