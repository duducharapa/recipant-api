package com.charapadev.recipant.domain.recipes.repositories;

import com.charapadev.recipant.domain.recipes.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
}
