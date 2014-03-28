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

    private List<Ingredient> ingredients;

    public RecipeAndIngredients() {
        ingredients = null;
    }

    public RecipeAndIngredients(Recipe recipe, List<Ingredient> ingredients) {
        super(recipe);
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


}
