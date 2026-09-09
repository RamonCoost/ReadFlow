package io.github.ramon.ReadFlow.business.dto.dashboard;

import io.github.ramon.ReadFlow.infrastructure.enums.Status;

import java.util.Map;

public record ResumoDashboardResponse(Long totalLivros,
                                      Map<Status, Long> totalLivroPorStatus) {
}
