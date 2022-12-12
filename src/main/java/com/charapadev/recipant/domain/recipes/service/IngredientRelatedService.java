package com.charapadev.recipant.domain.recipes.service;

import com.charapadev.recipant.domain.ingredients.entity.Ingredient;
import com.charapadev.recipant.domain.recipes.dto.ShowRelatedIngredientDTO;
import com.charapadev.recipant.domain.recipes.entity.Recipe;
import com.charapadev.recipant.domain.recipes.entity.RecipeIngredient;
import com.charapadev.recipant.domain.recipes.repository.RecipeIngredientRepository;
import com.charapadev.recipant.domain.recipes.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service used to manipulate the intermediary ingredient between recipes and ingredients.
 */

@Service
@AllArgsConstructor
public class IngredientRelatedService {

    private final RecipeIngredientRepository ingredientRelatedRepository;
    private final RecipeRepository recipeRepository;

    /**
     * Links a intermediary ingredient to a recipe.
     *
     * @param recipe The recipe identifier.
     * @param ingredient The intermediary ingredient.
     */

    public void addIngredientRelated(Recipe recipe, RecipeIngredient ingredient) {
        recipe.addIngredient(ingredient);
        recipeRepository.save(recipe);
    }

    /**
     * Creates a intermediary ingredient.
     *
     * @param ingredient The ingredient.
     * @param quantity The quantity of the ingredient on this recipe.
     * @return The created intermediary ingredient.
     */

    public RecipeIngredient create(Ingredient ingredient, float quantity) {
        RecipeIngredient ingredientRelated = new RecipeIngredient();
        ingredientRelated.setIngredient(ingredient);
        ingredientRelated.setQuantity(quantity);
        ingredientRelated = ingredientRelatedRepository.save(ingredientRelated);

        return ingredientRelated;
    }

    /**
     * Converts a intermediary ingredient to a presentable DTO.
     * It's used to remove some data irrelevant to be showed, turning it more readable to clients.
     *
     * @param ingredient The intermediary ingredient.
     * @return The intermediary ingredient as a DTO.
     */

    public ShowRelatedIngredientDTO convertToShowDTO(RecipeIngredient ingredient) {
        return new ShowRelatedIngredientDTO(
            ingredient.getId(),
            ingredient.getIngredient().getName(),
            ingredient.getQuantity()
        );
    }

}
