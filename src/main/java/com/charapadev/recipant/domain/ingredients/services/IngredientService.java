package com.charapadev.recipant.domain.ingredients.services;

import com.charapadev.recipant.domain.ingredients.dtos.CreateIngredientDTO;
import com.charapadev.recipant.domain.ingredients.entities.Ingredient;
import com.charapadev.recipant.domain.ingredients.repositories.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Ingredient create(CreateIngredientDTO createDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(createDTO.name());

        ingredient = ingredientRepository.save(ingredient);
        return ingredient;
    }

}
