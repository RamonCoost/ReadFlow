package io.github.ramon.ReadFlow.business.dto.googleApi;

import java.util.List;

public record GoogleBooksResponse(List<GoogleItemResponse> items) {
}
