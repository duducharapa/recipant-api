package com.charapadev.recipant.domain.ingredients.dto;

import javax.validation.constraints.NotBlank;

public record CreateIngredientDTO(
    @NotBlank String name
) {
}
