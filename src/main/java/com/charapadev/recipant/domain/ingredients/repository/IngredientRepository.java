package com.charapadev.recipant.domain.ingredients.repository;

import com.charapadev.recipant.domain.ingredients.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

}
