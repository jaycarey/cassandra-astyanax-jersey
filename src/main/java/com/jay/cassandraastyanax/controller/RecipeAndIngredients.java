package com.jay.cassandraastyanax.controller;

import com.jay.cassandraastyanax.domain.Ingredient;
import com.jay.cassandraastyanax.domain.Recipe;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author jaycarey
 */
@XmlRootElement
public class RecipeAndIngredients extends Recipe {

    private final List<Ingredient> ingredients;

    public RecipeAndIngredients(Recipe recipe, List<Ingredient> ingredients) {
        super(recipe);
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
