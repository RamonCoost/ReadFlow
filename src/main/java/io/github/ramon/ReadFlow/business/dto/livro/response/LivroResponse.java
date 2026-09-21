package io.github.ramon.ReadFlow.business.dto.livro.response;

import io.github.ramon.ReadFlow.infrastructure.enums.Status;

public record LivroResponse(Long id,

                            String titulo,

                            String autor,

                            String capa,

                            int totalPaginas,

                            int paginasLidas,

                            Status statusLeitura) {
}
