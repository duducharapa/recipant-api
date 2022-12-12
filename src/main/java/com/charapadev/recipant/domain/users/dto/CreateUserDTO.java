package com.charapadev.recipant.domain.users.dto;

import javax.validation.constraints.NotBlank;

public record CreateUserDTO(
    @NotBlank String email,
    @NotBlank String password
) {
}
