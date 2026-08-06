package io.github.ramon.ReadFlow.business.service.livro;

import io.github.ramon.ReadFlow.business.dto.livro.request.AtualizaLivroRequest;
import io.github.ramon.ReadFlow.business.dto.livro.request.AtualizaProgressoRequest;
import io.github.ramon.ReadFlow.business.dto.livro.request.LivroRequest;
import io.github.ramon.ReadFlow.business.dto.livro.response.LivroResponse;
import io.github.ramon.ReadFlow.business.mapper.livro.LivroMapper;
import io.github.ramon.ReadFlow.business.service.usuario.UsuarioService;
import io.github.ramon.ReadFlow.infrastructure.entity.livro.Livro;
import io.github.ramon.ReadFlow.infrastructure.entity.usuario.Usuario;
import io.github.ramon.ReadFlow.infrastructure.enums.Status;
import io.github.ramon.ReadFlow.infrastructure.exceptions.exception.BadRequestException;
import io.github.ramon.ReadFlow.infrastructure.exceptions.exception.ResourceNotFoundException;
import io.github.ramon.ReadFlow.infrastructure.repository.livro.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroMapper mapper;
    private final UsuarioService usuarioService;

    public LivroResponse salvarLivro(Long usuarioId, LivroRequest livroRequest) {

        Usuario usuario =  usuarioService.buscarUsuarioPorId(usuarioId);

        if (livroRequest.paginasLidas() > livroRequest.totalPaginas()) {
            throw new BadRequestException("Páginas lidas não podem ser maiores que o total de páginas do livro.");
        }

        Status status = calcularStatusLeitura(
                livroRequest.paginasLidas(),
                livroRequest.totalPaginas());

        Livro livro = mapper.paraLivro(livroRequest);

        livro.setUsuario(usuario);
        livro.setTitulo(normalizarTexto(livro.getTitulo()));
        livro.setAutor(normalizarTexto(livro.getAutor()));

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
        return mapper.paraLivroResponseList(repository.findByAutorContainingIgnoreCase(autor));
    }

    public LivroResponse atualizarLivro(long id, AtualizaLivroRequest atualizaLivroRequest) {
        Livro livro = buscarLivroEntityPorId(id);

        if (atualizaLivroRequest.paginasLidas() > atualizaLivroRequest.totalPaginas()) {
            throw new BadRequestException("Páginas lidas não podem ser maiores que o total de páginas do livro.");
        }

        livro.setTitulo(normalizarTexto(atualizaLivroRequest.titulo()));

        livro.setAutor(normalizarTexto(atualizaLivroRequest.autor()));

        livro.setTotalPaginas(atualizaLivroRequest.totalPaginas());

        livro.setPaginasLidas(atualizaLivroRequest.paginasLidas());

        if (atualizaLivroRequest.abandonado()) {
            livro.setStatusLeitura(Status.ABANDONEI);
        } else {
            Status status = calcularStatusLeitura(atualizaLivroRequest.paginasLidas(), atualizaLivroRequest.totalPaginas());
            livro.setStatusLeitura(status);
        }

        return mapper.paraLivroResponse(repository.save(livro));

    }

    public LivroResponse atualizarProgressoLeitura(long id, AtualizaProgressoRequest atualizaRequest) {
        Livro livro = buscarLivroEntityPorId(id);

        if (atualizaRequest.paginasLidas() > livro.getTotalPaginas()) {
            throw new BadRequestException("Páginas lidas não podem ser maiores que o total de páginas do livro.");
        }

        livro.setPaginasLidas(atualizaRequest.paginasLidas());

        Status status = calcularStatusLeitura(livro.getPaginasLidas(), livro.getTotalPaginas());

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

    private Status calcularStatusLeitura(int paginasLidas, int totalPaginas) {
        if (paginasLidas == totalPaginas) {
            return Status.CONCLUIDO;
        }

        if (paginasLidas == 0) {
            return Status.QUERO_LER;
        }

        return Status.LENDO;
    }

    private String normalizarTexto(String texto) {
        String[] palavrasDoTexto = texto.trim().split("\\s+");
        StringBuilder textoNormalizado = new StringBuilder();
        for (String palavra : palavrasDoTexto) {
            textoNormalizado.append(Character.toUpperCase(palavra.charAt(0)));
            textoNormalizado.append(palavra.substring(1).toLowerCase());
            textoNormalizado.append(" ");
        }
        return textoNormalizado.toString().trim();
    }

}
