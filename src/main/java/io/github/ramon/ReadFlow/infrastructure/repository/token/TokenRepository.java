package io.github.ramon.ReadFlow.infrastructure.repository.token;

import io.github.ramon.ReadFlow.infrastructure.entity.token.TokenEmailConfirmacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEmailConfirmacao, Long> {

    Optional<TokenEmailConfirmacao> findByToken(String token);
}
