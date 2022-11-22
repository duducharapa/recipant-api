package com.charapadev.recipant.domain.recipes.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateRecipeDTO(
    @NotBlank(message = "The recipe should has a valid name") String name
) {
}
