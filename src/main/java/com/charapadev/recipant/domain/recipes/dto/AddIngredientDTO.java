package com.charapadev.recipant.domain.recipes.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record AddIngredientDTO(
    @NotBlank String name,
    @NotNull @Positive Float quantity
) {
}
