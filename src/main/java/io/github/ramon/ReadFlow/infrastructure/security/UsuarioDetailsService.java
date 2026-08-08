package io.github.ramon.ReadFlow.infrastructure.security;

import io.github.ramon.ReadFlow.infrastructure.entity.usuario.Usuario;
import io.github.ramon.ReadFlow.infrastructure.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario =  repository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuario não encontrado")
        );
        UsuarioDetails usuarioDetails = new UsuarioDetails(usuario);
        return usuarioDetails;
    }

}
