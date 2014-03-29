package com.jay.cassandraastyanax.controller;

import com.jay.cassandraastyanax.domain.Ingredient;
import com.jay.cassandraastyanax.domain.Recipe;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.jay.beanmatcher.BeanMatcher.comparesTo;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author jaycarey
 */
public class RecipeAndIngredientsTest {

    private RecipeAndIngredients recipeAndIngredients;

    private Recipe recipe;

    private List<Ingredient> ingredients;

    @Before
    public void before() throws Exception {
        recipe = new Recipe("test recipe");
        ingredients = asList(
                new Ingredient(recipe.getId(), "carrots", 1, "item"),
                new Ingredient(recipe.getId(), "spinach", 1, "tin")
        );
        recipeAndIngredients = new RecipeAndIngredients(recipe, ingredients);
    }

    @Test
    public void canConstructRecipeAndIngredientsFromRecipeAndIngredients() throws Exception {
        assertThat(recipeAndIngredients.getRecipe(), comparesTo(recipe).onAllFields());
        assertThat(recipeAndIngredients.getIngredients(), equalTo(ingredients));
    }

    @Test
    public void canSerialiseAndDeserialise() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(recipeAndIngredients);

        RecipeAndIngredients deserialised = objectMapper.readValue(json, RecipeAndIngredients.class);

        assertThat(deserialised, comparesTo(recipeAndIngredients).onAllFields().exceptFor("ingredients"));
        assertThat(deserialised.getIngredients().get(0), comparesTo(recipeAndIngredients.getIngredients().get(0)).onAllFields());
        assertThat(deserialised.getIngredients().get(1), comparesTo(recipeAndIngredients.getIngredients().get(1)).onAllFields());
    }

}
