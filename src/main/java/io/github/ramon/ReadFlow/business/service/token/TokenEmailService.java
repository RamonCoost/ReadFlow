package io.github.ramon.ReadFlow.business.service.token;

import io.github.ramon.ReadFlow.infrastructure.entity.token.TokenEmailConfirmacao;
import io.github.ramon.ReadFlow.infrastructure.entity.usuario.Usuario;
import io.github.ramon.ReadFlow.infrastructure.exceptions.exception.BadRequestException;
import io.github.ramon.ReadFlow.infrastructure.exceptions.exception.ResourceNotFoundException;
import io.github.ramon.ReadFlow.infrastructure.repository.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenEmailService {

    private final TokenRepository repository;

    public TokenEmailConfirmacao criarToken(Usuario usuario){
        TokenEmailConfirmacao tokenEmailConfirmacao = new TokenEmailConfirmacao();
        String token = UUID.randomUUID().toString();
        LocalDateTime tokenCriado = LocalDateTime.now();
        LocalDateTime tokenExpirado = tokenCriado.plusMinutes(30);

        tokenEmailConfirmacao.setToken(token);
        tokenEmailConfirmacao.setTokenCriado(tokenCriado);
        tokenEmailConfirmacao.setTokenExpirado(tokenExpirado);
        tokenEmailConfirmacao.setUsuario(usuario);

        return repository.save(tokenEmailConfirmacao);
    }

    @Transactional
    public Usuario confirmarEmail(String token){
        TokenEmailConfirmacao confirmacaoEmail =
                repository.findByToken(token)
                        .orElseThrow(() -> new ResourceNotFoundException("token de confirmação não encontrado"));

        if(confirmacaoEmail.getTokenExpirado().isBefore(LocalDateTime.now())){
            throw new BadRequestException("token expirado");
        }

        Usuario usuario = confirmacaoEmail.getUsuario();
        repository.delete(confirmacaoEmail);
        return usuario;
    }
}
