package com.charapadev.recipant.domain.recipes.repositories;

import com.charapadev.recipant.domain.recipes.entities.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, UUID> {
}
