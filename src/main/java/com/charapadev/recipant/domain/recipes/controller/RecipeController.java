package com.charapadev.recipant.domain.recipes.controller;

import com.charapadev.recipant.domain.recipes.dto.*;
import com.charapadev.recipant.domain.recipes.service.RecipeService;
import com.charapadev.recipant.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@Api(value = "recipes")
@RequestMapping("recipes")
@AllArgsConstructor
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @ApiOperation(value = "Shows all recipes")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of recipes found", response = ShowRecipeDTO.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Error listing a recipes")
    })
    @GetMapping
    public ResponseEntity<List<ShowRecipeDTO>> list() {
        List<ShowRecipeDTO> recipes = recipeService.list();

        return ResponseEntity.ok(recipes);
    }

    @ApiOperation(value = "Creates a new recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Recipe created", response = ShowRecipeDTO.class),
        @ApiResponse(code = 500, message = "Error creating a new recipe")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ShowRecipeDTO> create(@Valid @RequestBody CreateRecipeDTO createDTO) {
        ShowRecipeDTO recipe = recipeService.create(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }

    @ApiOperation(value = "Searches a recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Recipe found", response = ShowRecipeDTO.class),
        @ApiResponse(code = 404, message = "Cannot found a recipe"),
        @ApiResponse(code = 500, message = "Error searching a recipe")
    })
    @GetMapping("/{recipeId}")
    public ResponseEntity<ShowRecipeDTO> find(@PathVariable("recipeId") UUID recipeId)
        throws ResourceNotFoundException
    {
        ShowRecipeDTO recipeFound = recipeService.findOneToShowOrFail(recipeId);

        return ResponseEntity.ok(recipeFound);
    }

    @ApiOperation(value = "Updates a recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Recipe updated"),
        @ApiResponse(code = 404, message = "Cannot found a recipe"),
        @ApiResponse(code = 500, message = "Error updating a recipe")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{recipeId}")
    public ResponseEntity<Void>  update(
        @PathVariable("recipeId") UUID recipeId,
        @Valid @RequestBody UpdateRecipeDTO updateDTO
    ) throws ResourceNotFoundException {
        recipeService.update(recipeId, updateDTO);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Removes a recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Recipe removed"),
        @ApiResponse(code = 404, message = "Cannot found a recipe"),
        @ApiResponse(code = 500, message = "Error removing a recipe")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> delete(@PathVariable("recipeId") UUID recipeId) throws ResourceNotFoundException {
        recipeService.delete(recipeId);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Adds a ingredient on recipe")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Ingredient added", response = ShowRelatedIngredientDTO.class),
        @ApiResponse(code = 404, message = "Cannot found a recipe"),
        @ApiResponse(code = 500, message = "Error adding a ingredient")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{recipeId}/ingredients")
    public ResponseEntity<ShowRelatedIngredientDTO> addIngredient(
        @PathVariable("recipeId") UUID recipeId,
        @Valid @RequestBody AddIngredientDTO addDTO
    ) throws ResourceNotFoundException {
        ShowRelatedIngredientDTO ingredient = recipeService.addIngredient(recipeId, addDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ingredient);
    }
}
