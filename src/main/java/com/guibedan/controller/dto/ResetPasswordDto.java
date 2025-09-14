package com.guibedan.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordDto(@NotBlank(message = "A nova senha não pode ser vazia")
                               @Size(min = 3, max = 100, message = "A senha deve ter entre 3 e 100 caracteres")
                               String newPassword) {
}
