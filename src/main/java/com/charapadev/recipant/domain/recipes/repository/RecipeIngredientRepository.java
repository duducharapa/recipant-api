package com.charapadev.recipant.domain.recipes.repository;

import com.charapadev.recipant.domain.recipes.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, UUID> {
}
