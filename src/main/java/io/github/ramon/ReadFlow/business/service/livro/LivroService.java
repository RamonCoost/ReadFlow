package io.github.ramon.ReadFlow.business.service.livro;

import io.github.ramon.ReadFlow.business.dto.livro.request.AtualizaLivroRequest;
import io.github.ramon.ReadFlow.business.dto.livro.request.AtualizaProgressoRequest;
import io.github.ramon.ReadFlow.business.dto.livro.request.LivroRequest;
import io.github.ramon.ReadFlow.business.dto.livro.response.LivroResponse;
import io.github.ramon.ReadFlow.business.mapper.livro.LivroMapper;
import io.github.ramon.ReadFlow.infrastructure.entity.livro.Livro;
import io.github.ramon.ReadFlow.infrastructure.entity.usuario.Usuario;
import io.github.ramon.ReadFlow.infrastructure.enums.Status;
import io.github.ramon.ReadFlow.infrastructure.exceptions.exception.BadRequestException;
import io.github.ramon.ReadFlow.infrastructure.exceptions.exception.ResourceNotFoundException;
import io.github.ramon.ReadFlow.infrastructure.repository.livro.LivroRepository;
import io.github.ramon.ReadFlow.infrastructure.security.UsuarioDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroMapper mapper;

    public LivroResponse salvarLivro(LivroRequest livroRequest) {

        Usuario usuario = buscarUsuarioAutenticado();

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

    public Page<LivroResponse> listarLivros(Pageable pageable, Status status) {
        Usuario usuario = buscarUsuarioAutenticado();
        Page<Livro> resultado;

        if (status != null) {
            resultado = repository.findByUsuarioAndStatusLeitura(usuario, status, pageable);
        } else {
            resultado = repository.findByUsuario(usuario, pageable);
        }

        Page<LivroResponse> resposta = resultado.map(
                livro -> mapper.paraLivroResponse(livro)
        );
        return resposta;
    }


    public LivroResponse buscarLivroPorId(Long id) {
        Usuario usuario = buscarUsuarioAutenticado();
        return mapper.paraLivroResponse(buscarLivroEntityPorId(id, usuario));
    }

    public List<LivroResponse> buscarLivroPorAutor(String autor) {
        Usuario usuario = buscarUsuarioAutenticado();
        return mapper.paraLivroResponseList(repository.findByAutorContainingIgnoreCaseAndUsuario(autor, usuario));
    }

    public LivroResponse atualizarLivro(long id, AtualizaLivroRequest atualizaLivroRequest) {
        Usuario usuario = buscarUsuarioAutenticado();

        Livro livro = buscarLivroEntityPorId(id, usuario);

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

        Usuario usuario = buscarUsuarioAutenticado();

        Livro livro = buscarLivroEntityPorId(id, usuario);

        if (atualizaRequest.paginasLidas() > livro.getTotalPaginas()) {
            throw new BadRequestException("Páginas lidas não podem ser maiores que o total de páginas do livro.");
        }

        livro.setPaginasLidas(atualizaRequest.paginasLidas());

        Status status = calcularStatusLeitura(livro.getPaginasLidas(), livro.getTotalPaginas());

        livro.setStatusLeitura(status);


        return mapper.paraLivroResponse(repository.save(livro));
    }

    public LivroResponse atualizarStatus(long id, Status status) {
        Usuario usuario = buscarUsuarioAutenticado();

        Livro livro = buscarLivroEntityPorId(id, usuario);
        livro.setStatusLeitura(status);
        return mapper.paraLivroResponse(
                repository.save(livro));

    }

    public void deletarLivro(Long id) {
        Usuario usuario = buscarUsuarioAutenticado();
        repository.delete(buscarLivroEntityPorId(id, usuario));
    }


    private Livro buscarLivroEntityPorId(Long id, Usuario usuario) {
        return repository.findByIdAndUsuario(id, usuario).orElseThrow(
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

    private Usuario buscarUsuarioAutenticado() {

        UsuarioDetails usuarioDetails =
                (UsuarioDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return usuarioDetails.getUsuario();
    }

}
