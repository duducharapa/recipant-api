package com.charapadev.recipant.domain.recipes.controllers;

import com.charapadev.recipant.domain.recipes.dtos.CreateRecipeDTO;
import com.charapadev.recipant.domain.recipes.dtos.UpdateRecipeDTO;
import com.charapadev.recipant.domain.recipes.entities.Recipe;
import com.charapadev.recipant.domain.recipes.services.RecipeService;
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

    @GetMapping()
    public ResponseEntity<List<Recipe>> list() {
        List<Recipe> recipes = recipeService.list();

        return ResponseEntity.ok(recipes);
    }

    @PostMapping()
    public ResponseEntity<Recipe> create(@Valid @RequestBody CreateRecipeDTO createDTO) {
        Recipe recipe = recipeService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> find(@PathVariable("recipeId") UUID recipeId) {
        Recipe recipeFound = recipeService.findOneOrFail(recipeId);

        return ResponseEntity.ok(recipeFound);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<Void>  update(
        @PathVariable("recipeId") UUID recipeId,
        @Valid @RequestBody UpdateRecipeDTO updateDTO
    ) {
        recipeService.update(recipeId, updateDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> delete(@PathVariable("recipeId") UUID recipeId) {
        recipeService.delete(recipeId);

        return ResponseEntity.noContent().build();
    }
}
