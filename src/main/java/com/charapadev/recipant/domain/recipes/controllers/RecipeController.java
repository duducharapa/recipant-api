package com.charapadev.recipant.domain.recipes.controllers;

import com.charapadev.recipant.domain.recipes.dtos.*;
import com.charapadev.recipant.domain.recipes.services.RecipeService;
import com.charapadev.recipant.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<ShowRecipeDTO>> list() {
        List<ShowRecipeDTO> recipes = recipeService.list();

        return ResponseEntity.ok(recipes);
    }

    @PostMapping
    public ResponseEntity<ShowRecipeDTO> create(@Valid @RequestBody CreateRecipeDTO createDTO) {
        ShowRecipeDTO recipe = recipeService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<ShowRecipeDTO> find(@PathVariable("recipeId") UUID recipeId)
        throws ResourceNotFoundException
    {
        ShowRecipeDTO recipeFound = recipeService.findOneToShowOrFail(recipeId);

        return ResponseEntity.ok(recipeFound);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<Void>  update(
        @PathVariable("recipeId") UUID recipeId,
        @Valid @RequestBody UpdateRecipeDTO updateDTO
    ) throws ResourceNotFoundException {
        recipeService.update(recipeId, updateDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> delete(@PathVariable("recipeId") UUID recipeId) throws ResourceNotFoundException {
        recipeService.delete(recipeId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{recipeId}/ingredients")
    public ResponseEntity<ShowRelatedIngredientDTO> addIngredient(
        @PathVariable("recipeId") UUID recipeId,
        @Valid @RequestBody AddIngredientDTO addDTO
    ) throws ResourceNotFoundException {
        ShowRelatedIngredientDTO ingredient = recipeService.addIngredient(recipeId, addDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ingredient);
    }
}
