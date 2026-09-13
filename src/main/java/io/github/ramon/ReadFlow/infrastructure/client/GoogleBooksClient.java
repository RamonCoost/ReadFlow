package io.github.ramon.ReadFlow.infrastructure.client;


import io.github.ramon.ReadFlow.business.dto.googleApi.GoogleBooksResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GoogleBooksClient {

    @Value("${google.books.api}")
    String keyApi;

    RestClient restClient = RestClient.builder()
            .baseUrl("https://www.googleapis.com/books/v1")
            .build();

    public GoogleBooksResponse buscarLivro(String termo) {
        GoogleBooksResponse resposta = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/volumes")
                        .queryParam("q", "intitle:" + termo)
                        .queryParam("key", keyApi)
                        .build())
                .retrieve()
                .body(GoogleBooksResponse.class);
        return resposta;
    }
}