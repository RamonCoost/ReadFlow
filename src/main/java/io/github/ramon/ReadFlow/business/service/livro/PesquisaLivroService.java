package io.github.ramon.ReadFlow.business.service.livro;

import io.github.ramon.ReadFlow.business.dto.googleApi.GoogleBooksResponse;
import io.github.ramon.ReadFlow.business.dto.livro.response.PesquisaLivroResponse;
import io.github.ramon.ReadFlow.infrastructure.client.GoogleBooksClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisaLivroService {

    private final GoogleBooksClient googleBooksClient;

    public List<PesquisaLivroResponse> pesquisarLivro(String termo) {
        GoogleBooksResponse resposta = googleBooksClient.buscarLivro(termo);

        if (resposta.items() == null) {
            return List.of();
        }

        return resposta.items().stream()
                .map(item -> item.volumeInfo())
                .map(volumeInfo ->  new PesquisaLivroResponse(
                        volumeInfo.title(),
                        volumeInfo.authors(),
                        volumeInfo.imageLinks() != null ? volumeInfo.imageLinks().thumbnail() : null,
                        volumeInfo.description(),
                        volumeInfo.publisher(),
                        volumeInfo.pageCount())
                ).toList();
    }
}
