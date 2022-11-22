package com.charapadev.recipant.domain.recipes.services;

import com.charapadev.recipant.domain.recipes.dtos.CreateRecipeDTO;
import com.charapadev.recipant.domain.recipes.dtos.UpdateRecipeDTO;
import com.charapadev.recipant.domain.recipes.entities.Recipe;
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

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> list() {
        return recipeRepository.findAll();
    }

    public Recipe findOneOrFail(UUID id) {
        return recipeRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Cannot found a Recipe with the given ID: " + id));
    }

    public void delete(UUID id) {
        boolean recipeNotExists = !recipeRepository.existsById(id);

        if (recipeNotExists) {
            throw new NoSuchElementException("Cannot found a Recipe with the given ID: " + id);
        }

        recipeRepository.deleteById(id);
    }

    public void update(UUID id, UpdateRecipeDTO updateDTO) {
        Recipe recipe = findOneOrFail(id);

        try {
            boolean nameNotBlank = !( updateDTO.name().isBlank() );
            if (nameNotBlank) {
                recipe.setName(updateDTO.name());
            }

            recipeRepository.save(recipe);
        } catch (NullPointerException ignored) {}
    }

    public Recipe create(CreateRecipeDTO createDTO) {
        Recipe recipe = new Recipe();
        recipe.setName(createDTO.name());
        recipe = recipeRepository.save(recipe);

        return recipe;
    }

}
