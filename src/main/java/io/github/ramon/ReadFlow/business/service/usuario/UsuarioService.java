package io.github.ramon.ReadFlow.business.service.usuario;

import io.github.ramon.ReadFlow.business.dto.usuario.request.AtualizarUsuarioRequest;
import io.github.ramon.ReadFlow.business.dto.usuario.request.CadastroUsuarioRequest;
import io.github.ramon.ReadFlow.business.dto.usuario.response.UsuarioResponse;
import io.github.ramon.ReadFlow.business.mapper.usuario.UsuarioMapper;
import io.github.ramon.ReadFlow.infrastructure.entity.usuario.Usuario;
import io.github.ramon.ReadFlow.infrastructure.exceptions.exception.ConflictException;
import io.github.ramon.ReadFlow.infrastructure.repository.usuario.UsuarioRepository;
import io.github.ramon.ReadFlow.infrastructure.security.UsuarioDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse salvarUsuario(CadastroUsuarioRequest cadastroUsuarioRequest) {

        Usuario usuario = mapper.paraUsuario(cadastroUsuarioRequest);
        usuario.setNome(normalizarTexto(usuario.getNome()));
        usuario.setEmail(normalizarEmail(usuario.getEmail()));

        if (repository.existsByEmail(usuario.getEmail())) {
            throw new ConflictException("Esse email já esta cadastrado");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return mapper.paraUsuarioResponse(repository.save(usuario));
    }

    public UsuarioResponse atualizarUsuario(AtualizarUsuarioRequest atualizarUsuarioRequest) {

        Usuario usuario = buscarUsuarioAutenticado();
        usuario.setNome(normalizarTexto(atualizarUsuarioRequest.nome()));
        usuario.setSenha(passwordEncoder.encode(atualizarUsuarioRequest.senha()));

        return mapper.paraUsuarioResponse(repository.save(usuario));
    }

    @Transactional
    public void deletarUsuario() {
        Usuario usuario = buscarUsuarioAutenticado();
        repository.delete(usuario);
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

    private String normalizarEmail(String email) {
        return email.toLowerCase().trim();
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
