package com.jay.cassandraastyanax.controller;

import com.jay.cassandraastyanax.domain.Ingredient;
import com.jay.cassandraastyanax.domain.Recipe;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author jaycarey
 */
@XmlRootElement
public class RecipeAndIngredients {

    private Recipe recipe;

    private List<Ingredient> ingredients;

    public RecipeAndIngredients() {
    }

    public RecipeAndIngredients(Recipe recipe, List<Ingredient> ingredients) {
        this.recipe = recipe;
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
