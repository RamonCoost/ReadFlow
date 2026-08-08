package io.github.ramon.ReadFlow.business.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank
                           @Email
                           String email,

                           @NotBlank
                           String senha) {
}
