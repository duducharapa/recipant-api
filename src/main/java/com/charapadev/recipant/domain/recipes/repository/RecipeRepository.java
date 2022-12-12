package com.charapadev.recipant.domain.recipes.repository;

import com.charapadev.recipant.domain.recipes.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
}
