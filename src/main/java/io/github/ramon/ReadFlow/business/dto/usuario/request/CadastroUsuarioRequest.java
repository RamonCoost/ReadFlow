package io.github.ramon.ReadFlow.business.dto.usuario.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastroUsuarioRequest(@NotBlank
                                     String nome,

                                     @NotBlank
                                     @Email
                                     String email,

                                     @NotBlank
                                     String senha) {
}
