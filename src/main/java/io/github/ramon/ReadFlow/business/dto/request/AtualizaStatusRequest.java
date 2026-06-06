package io.github.ramon.ReadFlow.business.dto.request;

import io.github.ramon.ReadFlow.infrastructure.enums.Status;
import jakarta.validation.constraints.NotNull;

public record AtualizaStatusRequest(@NotNull Status statusLeitura) {
}
