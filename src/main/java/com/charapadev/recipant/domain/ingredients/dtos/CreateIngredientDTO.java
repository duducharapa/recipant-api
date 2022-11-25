package com.charapadev.recipant.domain.ingredients.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateIngredientDTO(
    @NotBlank(message = "The ingredient should has a valid name") String name
) {
}
