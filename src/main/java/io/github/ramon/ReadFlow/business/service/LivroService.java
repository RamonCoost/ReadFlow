package io.github.ramon.ReadFlow.business.service;

import io.github.ramon.ReadFlow.business.dto.request.AtualizaProgressoRequest;
import io.github.ramon.ReadFlow.business.dto.request.LivroRequest;
import io.github.ramon.ReadFlow.business.dto.response.LivroResponse;
import io.github.ramon.ReadFlow.business.mapper.LivroMapper;
import io.github.ramon.ReadFlow.infrastructure.entity.Livro;
import io.github.ramon.ReadFlow.infrastructure.enums.Status;
import io.github.ramon.ReadFlow.infrastructure.exceptions.BadRequestException;
import io.github.ramon.ReadFlow.infrastructure.exceptions.ResourceNotFoundException;
import io.github.ramon.ReadFlow.infrastructure.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroMapper mapper;

    public LivroResponse salvarLivro(LivroRequest livroRequest) {

        if (livroRequest.paginasLidas() > livroRequest.totalPaginas()) {
            throw new BadRequestException("Páginas lidas não podem ser maiores que o total de páginas do livro.");
        }

        Status status = calcualarStatusLeitura(
                livroRequest.paginasLidas(),
                livroRequest.totalPaginas());

        Livro livro = mapper.paraLivro(livroRequest);

        livro.setStatusLeitura(status);

        return mapper.paraLivroResponse(repository.save(livro));
    }

    public List<LivroResponse> listarLivros() {
        return mapper.paraLivroResponseList(repository.findAll());
    }


    public LivroResponse buscarLivroPorId(Long id) {
        return mapper.paraLivroResponse(buscarLivroEntityPorId(id)
        );
    }

    public List<LivroResponse> buscarLivroPorAutor(String autor) {
        return mapper.paraLivroResponseList(repository.findByAutor(autor));
    }

    public LivroResponse atualizarProgressoLeitura(long id, AtualizaProgressoRequest atualizaRequest) {
        Livro livro = buscarLivroEntityPorId(id);

        if (atualizaRequest.paginasLidas() > livro.getTotalPaginas()) {
            throw new BadRequestException("Páginas lidas não podem ser maiores que o total de páginas do livro.");
        }

        livro.setPaginasLidas(atualizaRequest.paginasLidas());

        Status status = calcualarStatusLeitura(livro.getPaginasLidas(), livro.getTotalPaginas());

        livro.setStatusLeitura(status);


        return mapper.paraLivroResponse(repository.save(livro));
    }

    public LivroResponse atualizarStatus(long id, Status status) {
        Livro livro = buscarLivroEntityPorId(id);
        livro.setStatusLeitura(status);
        return mapper.paraLivroResponse(
                repository.save(livro));

    }

    public void deletarLivro(Long id) {
        repository.delete(buscarLivroEntityPorId(id));
    }


    private Livro buscarLivroEntityPorId(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Livro não encontrado, verifique o id do livro."));
    }

    private Status calcualarStatusLeitura(int paginasLidas, int totalPaginas) {
        if (paginasLidas == totalPaginas) {
            return Status.CONCLUIDO;
        }

        if (paginasLidas == 0) {
            return Status.QUERO_LER;
        }

        return Status.LENDO;
    }
}
