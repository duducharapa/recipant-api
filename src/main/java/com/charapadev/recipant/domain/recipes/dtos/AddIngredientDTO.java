package com.charapadev.recipant.domain.recipes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddIngredientDTO(
    @NotBlank String name,
    @NotNull @Positive Float quantity
) {
}
