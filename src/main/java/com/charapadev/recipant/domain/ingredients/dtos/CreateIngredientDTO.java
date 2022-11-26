package com.charapadev.recipant.domain.ingredients.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateIngredientDTO(
    @NotBlank String name
) {
}
