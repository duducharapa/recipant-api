package com.charapadev.recipant.domain.recipes.service;

import com.charapadev.recipant.domain.ingredients.dto.CreateIngredientDTO;
import com.charapadev.recipant.domain.ingredients.entity.Ingredient;
import com.charapadev.recipant.domain.ingredients.service.IngredientService;
import com.charapadev.recipant.domain.recipes.dto.*;
import com.charapadev.recipant.domain.recipes.entity.Recipe;
import com.charapadev.recipant.domain.recipes.entity.RecipeIngredient;
import com.charapadev.recipant.domain.recipes.repository.RecipeRepository;
import com.charapadev.recipant.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service used to manipulate the recipes instance.
 */

@Service
@AllArgsConstructor
@Slf4j(topic = "Recipe service")
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    private IngredientRelatedService ingredientRelatedService;

    @Autowired
    private IngredientService ingredientService;

    /**
     * Converts an instance of recipe to a presentable DTO.
     *
     * @param recipe The recipe identifier.
     * @return The converted recipe as a DTO.
     */

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
     * Checks the existence of a recipe using a given identifier..
     * Throws an error if not found anything.
     *
     * @param recipeId The recipe identifier.
     * @throws ResourceNotFoundException If recipe not exists.
     */
    private void existsOrFail(UUID recipeId) throws ResourceNotFoundException {
        boolean recipeNotExists = !recipeRepository.existsById(recipeId);

        if (recipeNotExists) {
            log.error("Cannot found a recipe with ID: {}", recipeId);
            throw new ResourceNotFoundException("Cannot found a Recipe with ID: " + recipeId);
        }
    }

    /**
     * Shows all recipes registered.
     *
     * @return The list of recipes found.
     */

    public List<ShowRecipeDTO> list() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream()
            .map(this::convertToShowDTO)
            .toList();
    }

    /**
     * Tries to find a recipe on application using a given identifier.
     * Throws an error if not found anything.
     *
     * @param recipeId The recipe identifier.
     * @return The recipe found.
     * @throws ResourceNotFoundException When cannot found a recipe.
     */

    public Recipe findOneOrFail(UUID recipeId) {
        return recipeRepository.findById(recipeId)
            .orElseThrow(() -> {
                log.error("Cannot found a recipe with ID: {}", recipeId);
                throw new ResourceNotFoundException("Cannot found a recipe with ID: " + recipeId);
            });
    }

    /**
     * Searches a recipe and show it as a presentable DTO using a given identifier.
     *
     * @param recipeId The recipe identifier.
     * @return The recipe find as a DTO.
     */

    public ShowRecipeDTO findOneToShowOrFail(UUID recipeId) {
        Recipe recipe = findOneOrFail(recipeId);

        return convertToShowDTO(recipe);
    }

    /**
     * Removes a recipe using a given identifier.
     *
     * @param recipeId The recipe identifier.
     */

    public void delete(UUID recipeId) {
        existsOrFail(recipeId);

        recipeRepository.deleteById(recipeId);
        log.info("Removed a recipe with ID: {}", recipeId);
    }

    /**
     * Changes some information about a existent recipe.
     *
     * @param recipeId The recipe identifier.
     * @param updateDTO The information to override.
     * @throws ResourceNotFoundException When cannot find a recipe.
     */

    public void update(UUID recipeId, UpdateRecipeDTO updateDTO) throws ResourceNotFoundException {
        Recipe recipe = findOneOrFail(recipeId);

        // TODO: Find a better logic to change only the not blank values without produce NullPointerException
        try {
            boolean nameNotBlank = !( updateDTO.name().isBlank() );
            if (nameNotBlank) {
                recipe.setName(updateDTO.name());
            }

            recipe = recipeRepository.save(recipe);
            log.info("Updated a recipe: {}", recipe);
        } catch (NullPointerException ignored) {}
    }

    /**
     * Inserts a new recipe.
     *
     * @param createDTO The recipe creation information.
     * @return The created recipe.
     */

    public ShowRecipeDTO create(CreateRecipeDTO createDTO) {
        Recipe recipe = new Recipe();

        recipe.setName(createDTO.name());
        recipe = recipeRepository.save(recipe);

        log.info("Created a new recipe: {}", recipe);
        return convertToShowDTO(recipe);
    }

    /**
     * Connects a valid ingredient to a valid recipe.
     * Because the relation of these entities are <b>many to many</b>, we need
     * to create an intermediary ingredient called RecipeIngredient, that can be explained
     * as: "the information of an ingredient on a recipe, like the measuring and quantities".
     *
     * @param recipeId The recipe identifier.
     * @param addDTO The information about the ingredient to add.
     * @return The intermediary ingredient created.
     * @throws ResourceNotFoundException If cannot find an user or cannot find an ingredient.
     */

    public ShowRelatedIngredientDTO addIngredient(UUID recipeId, AddIngredientDTO addDTO)
        throws ResourceNotFoundException
    {
        Recipe recipe = findOneOrFail(recipeId);

        CreateIngredientDTO createIngredientDTO = new CreateIngredientDTO(addDTO.name());
        Ingredient ingredient = ingredientService.create(createIngredientDTO);

        RecipeIngredient ingredientRelated = ingredientRelatedService.create(ingredient, addDTO.quantity());
        ingredientRelatedService.addIngredientRelated(recipe, ingredientRelated);

        log.info("Added an ingredient to recipe with ID {}: {}", recipeId, ingredientRelated);
        return ingredientRelatedService.convertToShowDTO(ingredientRelated);
    }

}
