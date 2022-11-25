package com.charapadev.recipant.domain.recipes.services;

import com.charapadev.recipant.domain.ingredients.entities.Ingredient;
import com.charapadev.recipant.domain.recipes.dtos.ShowRelatedIngredientDTO;
import com.charapadev.recipant.domain.recipes.entities.Recipe;
import com.charapadev.recipant.domain.recipes.entities.RecipeIngredient;
import com.charapadev.recipant.domain.recipes.repositories.RecipeIngredientRepository;
import com.charapadev.recipant.domain.recipes.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IngredientRelatedService {

    private final RecipeIngredientRepository ingredientRelatedRepository;
    private final RecipeRepository recipeRepository;

    /**
     * Links a related ingredient created to a existent recipe.
     *
     * @param recipe The recipe identifier.
     * @param ingredient The related ingredient.
     */

    public void addIngredientRelated(Recipe recipe, RecipeIngredient ingredient) {
        recipe.addIngredient(ingredient);
        recipeRepository.save(recipe);
    }

    /**
     * Creates a related ingredient to be added on a existent recipe.
     *
     * @param ingredient The ingredient instance.
     * @param quantity The quantity of that ingredient on this recipe.
     * @return The related ingredient crated.
     */

    public RecipeIngredient create(Ingredient ingredient, float quantity) {
        RecipeIngredient ingredientRelated = new RecipeIngredient();
        ingredientRelated.setIngredient(ingredient);
        ingredientRelated.setQuantity(quantity);
        ingredientRelated = ingredientRelatedRepository.save(ingredientRelated);

        return ingredientRelated;
    }

    /**
     * Converts a related ingredient to a presentable DTO.
     * <p>
     * It's used to remove some data irrelevant to user and turn it more readable to clients.
     *
     * @param ingredient The related ingredient.
     * @return The presentable DTO.
     */

    public ShowRelatedIngredientDTO convertToShowDTO(RecipeIngredient ingredient) {
        return new ShowRelatedIngredientDTO(
            ingredient.getId(),
            ingredient.getIngredient().getName(),
            ingredient.getQuantity()
        );
    }

}
