package io.github.ramon.ReadFlow.infrastructure.client;


import io.github.ramon.ReadFlow.business.dto.googleApi.GoogleBooksResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GoogleBooksClient {

    @Value("${google.books.proxy.url}")
    private String proxyUrl;

    @Value("${google.books.proxy.token}")
    private String proxyToken;

    public GoogleBooksResponse buscarLivro(String termo) {
        RestClient restClient = RestClient.builder()
            .baseUrl(proxyUrl)
            .build();
        GoogleBooksResponse resposta = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", termo)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + proxyToken)
                .retrieve()
                .body(GoogleBooksResponse.class);
        return resposta;
    }
}