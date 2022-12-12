package com.charapadev.recipant.domain.recipes.dto;

import java.util.UUID;

public record ShowRelatedIngredientDTO (
    UUID id,
    String name,
    float quantity
) {
}
