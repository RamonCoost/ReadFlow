package io.github.ramon.ReadFlow.business.dto.googleApi;

import java.util.List;

public record GoogleVolumeInfoResponse(String title,
                                       List<String> authors,
                                       String description,
                                       String publisher,
                                       GoogleImageLinksResponse imageLinks,
                                       List<String> categories,
                                       List<GoogleIndustryIdentifiersResponse> industryIdentifiers,
                                       Integer pageCount) {
}
