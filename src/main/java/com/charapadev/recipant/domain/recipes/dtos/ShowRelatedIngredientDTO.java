package com.charapadev.recipant.domain.recipes.dtos;

import java.util.UUID;

public record ShowRelatedIngredientDTO (
    UUID id,
    String name,
    float quantity
) {
}
