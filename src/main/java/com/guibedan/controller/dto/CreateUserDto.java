package com.guibedan.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(@Email String username,
                            @NotBlank(message = "A senha não pode ser vazia")
                            @Size(min = 3, max = 100, message = "A senha deve ter entre 3 e 100 caracteres")
                            String password) {
}
