package com.charapadev.recipant.domain.recipes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddIngredientDTO(
    @NotBlank(message = "The ingredient must has a valid name") String name,
    @NotNull(message = "The ingredient must has a valid quantity")
        @Positive(message = "The quantity of the ingredient must be a positive value") Float quantity
) {
}
