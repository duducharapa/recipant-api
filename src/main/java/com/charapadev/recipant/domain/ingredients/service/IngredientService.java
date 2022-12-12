package com.charapadev.recipant.domain.ingredients.service;

import com.charapadev.recipant.domain.ingredients.dto.CreateIngredientDTO;
import com.charapadev.recipant.domain.ingredients.entity.Ingredient;
import com.charapadev.recipant.domain.ingredients.repository.IngredientRepository;
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
