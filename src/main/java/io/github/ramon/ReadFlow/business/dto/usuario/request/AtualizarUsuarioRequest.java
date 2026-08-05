package io.github.ramon.ReadFlow.business.dto.usuario.request;

import jakarta.validation.constraints.NotBlank;

public record AtualizarUsuarioRequest(@NotBlank
                                     String nome,

                                      @NotBlank
                                     String senha) {
}
