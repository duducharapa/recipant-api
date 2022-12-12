package com.charapadev.recipant.domain.recipes.dto;

import javax.validation.constraints.NotBlank;

public record CreateRecipeDTO(
    @NotBlank String name
) {

}
