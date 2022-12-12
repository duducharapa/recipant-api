package com.charapadev.recipant.domain.recipes.entity;

import com.charapadev.recipant.domain.ingredients.entity.Ingredient;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class RecipeIngredient implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Ingredient ingredient;

    @Column
    private Float quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
