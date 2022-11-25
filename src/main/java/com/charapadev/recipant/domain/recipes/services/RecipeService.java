package com.charapadev.recipant.domain.recipes.services;

import com.charapadev.recipant.domain.ingredients.dtos.CreateIngredientDTO;
import com.charapadev.recipant.domain.ingredients.entities.Ingredient;
import com.charapadev.recipant.domain.ingredients.services.IngredientService;
import com.charapadev.recipant.domain.recipes.dtos.*;
import com.charapadev.recipant.domain.recipes.entities.Recipe;
import com.charapadev.recipant.domain.recipes.entities.RecipeIngredient;
import com.charapadev.recipant.domain.recipes.repositories.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    private IngredientRelatedService ingredientRelatedService;

    @Autowired
    private IngredientService ingredientService;

    private ShowRecipeDTO convertToShowDTO(Recipe recipe) {
        List<ShowRelatedIngredientDTO> relatedIngredients = recipe.getIngredients().stream()
            .map(ingredientRelatedService::convertToShowDTO)
            .toList();

        return new ShowRecipeDTO(
            recipe.getId(),
            recipe.getName(),
            relatedIngredients
        );
    }

    /**
     * Verifies the existence of a recipe with the given identifier.
     *
     * <p>
     * This method is used when the data about the recipe is not relevant for the process,
     * but REQUIRES to exist.
     * </p>
     *
     * @param recipeId The recipe identifier.
     */
    private void existsOrFail(UUID recipeId) {
        boolean recipeNotExists = !recipeRepository.existsById(recipeId);

        if (recipeNotExists) {
            throw new NoSuchElementException("Cannot found a Recipe with the given ID: " + recipeId);
        }
    }

    /**
     * Searches and expose a list of recipes registered.
     *
     * @return The list of recipes registered.
     */

    public List<ShowRecipeDTO> list() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream()
            .map(this::convertToShowDTO)
            .toList();
    }

    /**
     * Searches and return a Recipe found using the given identifier.
     *
     * <p>
     * This method is used when the process DEPENDS on a existent recipe. Else, throws a error.
     * </p>
     *
     * @param recipeId The recipe identifier.
     * @return The recipe found.
     */

    public Recipe findOneOrFail(UUID recipeId) {
        return recipeRepository.findById(recipeId)
            .orElseThrow(() -> new NoSuchElementException("Cannot found a Recipe with the given ID: " + recipeId));
    }

    /**
     * Searches and return a recipe found as a presentable DTO using the given identifier.
     * <p>
     * This method is used to expose a EXISTENT recipe to clients by the DTO pattern.
     *
     * @param recipeId The recipe identifier.
     * @return The presentable DTO of recipe found.
     */

    public ShowRecipeDTO findOneToShowOrFail(UUID recipeId) {
        Recipe recipe = findOneOrFail(recipeId);

        return convertToShowDTO(recipe);
    }

    /**
     * Removes a existent recipe.
     *
     * @param recipeId The recipe identifier.
     */

    public void delete(UUID recipeId) {
        existsOrFail(recipeId);

        recipeRepository.deleteById(recipeId);
    }

    /**
     * Changes some information about a existent recipe.
     *
     * @param recipeId The recipe identifier.
     * @param updateDTO The additional data to be changed on recipe.
     */

    public void update(UUID recipeId, UpdateRecipeDTO updateDTO) {
        Recipe recipe = findOneOrFail(recipeId);

        // TODO: Find a better logic to change only the not blank values
        try {
            boolean nameNotBlank = !( updateDTO.name().isBlank() );
            if (nameNotBlank) {
                recipe.setName(updateDTO.name());
            }

            recipeRepository.save(recipe);
        } catch (NullPointerException ignored) {}
    }

    /**
     * Creates and returns a new instance of recipe.
     *
     * @param createDTO The information to create.
     * @return The recipe created.
     */

    public ShowRecipeDTO create(CreateRecipeDTO createDTO) {
        Recipe recipe = new Recipe();

        recipe.setName(createDTO.name());
        recipe = recipeRepository.save(recipe);

        return convertToShowDTO(recipe);
    }

    /**
     * Adds a new related ingredient on a existent recipe.
     *
     * @param recipeId The recipe identifier.
     * @param addDTO The information about the ingredient to add.
     * @return The related ingredient created.
     */

    public ShowRelatedIngredientDTO addIngredient(UUID recipeId, AddIngredientDTO addDTO) {
        Recipe recipe = findOneOrFail(recipeId);

        CreateIngredientDTO createIngredientDTO = new CreateIngredientDTO(addDTO.name());
        Ingredient ingredient = ingredientService.create(createIngredientDTO);

        RecipeIngredient ingredientRelated = ingredientRelatedService.create(ingredient, addDTO.quantity());
        ingredientRelatedService.addIngredientRelated(recipe, ingredientRelated);

        return ingredientRelatedService.convertToShowDTO(ingredientRelated);
    }

}
