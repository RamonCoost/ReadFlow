package io.github.ramon.ReadFlow.infrastructure.exceptions.response;

import java.time.LocalDateTime;

public record ErrorResponse(int status,

                            String erro,

                            String mensagem,

                            LocalDateTime dataHora) {
}
