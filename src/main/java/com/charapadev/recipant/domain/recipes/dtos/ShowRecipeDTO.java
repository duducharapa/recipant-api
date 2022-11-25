package com.charapadev.recipant.domain.recipes.dtos;

import java.util.List;
import java.util.UUID;

public record ShowRecipeDTO (
    UUID id,
    String name,
    List<ShowRelatedIngredientDTO> ingredients
) {
}
