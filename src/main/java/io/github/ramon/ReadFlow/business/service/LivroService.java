package io.github.ramon.ReadFlow.business.service;

import io.github.ramon.ReadFlow.business.dto.AtualizaProgressoRequest;
import io.github.ramon.ReadFlow.business.dto.LivroRequest;
import io.github.ramon.ReadFlow.business.dto.LivroResponse;
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
        return mapper.paraLivroResponse(
                repository.save(mapper.paraLivro(livroRequest))
        );
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

    public LivroResponse atualizarProgressoLeitura(long id, AtualizaProgressoRequest request) {
        Livro livro = buscarLivroEntityPorId(id);

        if (request.paginasLidas() > livro.getTotalPaginas()){
            throw new BadRequestException("Páginas lidas não podem ser maiores que o total de páginas do livro.");
        }

        livro.setPaginasLidas(request.paginasLidas());

        if(livro.getPaginasLidas() == livro.getTotalPaginas()){
            livro.setStatusLeitura(Status.CONCLUIDO);
        }

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
}
