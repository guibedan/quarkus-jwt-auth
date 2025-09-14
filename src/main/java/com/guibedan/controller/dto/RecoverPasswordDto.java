package com.guibedan.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record RecoverPasswordDto(@NotBlank(message = "O email não pode estar vazio") String username) {
}
