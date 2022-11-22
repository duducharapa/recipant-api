package com.charapadev.recipant.domain.recipes.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    public boolean equals(Recipe recipe) {
        return recipe.id.equals(this.id);
    }

}
