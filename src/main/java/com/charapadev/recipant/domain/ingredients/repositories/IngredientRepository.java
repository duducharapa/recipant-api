package com.charapadev.recipant.domain.ingredients.repositories;

import com.charapadev.recipant.domain.ingredients.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

}
