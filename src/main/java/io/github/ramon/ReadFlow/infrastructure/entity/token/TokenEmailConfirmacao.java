package io.github.ramon.ReadFlow.infrastructure.entity.token;

import io.github.ramon.ReadFlow.infrastructure.entity.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "token_email")
public class TokenEmailConfirmacao {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "token", nullable = false, unique = true)
    private String token;
    @Column(name = "token_criado", nullable = false)
    private LocalDateTime tokenCriado;
    @Column(name = "token_expirado", nullable = false)
    private LocalDateTime tokenExpirado;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
