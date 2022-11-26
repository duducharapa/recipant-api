package com.charapadev.recipant.domain.recipes.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateRecipeDTO(
    @NotBlank String name
) {

}
