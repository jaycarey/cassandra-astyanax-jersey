package com.jay.cassandraastyanax.domain;

import com.jay.cassandraastyanax.controller.RecipeAndIngredients;
import org.apache.cassandra.utils.UUIDGen;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.jay.cassandraastyanax.TestUtils.matchesPattern;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * @author jaycarey
 */
public class IngredientTest {

    private Ingredient ingredient;

    private UUID recipeId;

    @Before
    public void before() throws Exception {
        recipeId = UUIDGen.getTimeUUID();
        ingredient = new Ingredient(
                recipeId,
                "tomatoes",
                10,
                "items"
        );
    }

    @Test
    public void newRecipeHasCorrectValues() throws Exception {
        assertThat(ingredient.getRecipeId(), equalTo(recipeId));
        assertThat(ingredient.getName(), equalTo("tomatoes"));
        assertThat(ingredient.getQuantity(), equalTo(10));
        assertThat(ingredient.getUnit(), equalTo("items"));
    }

    @Test
    public void producesHumanReadableString() throws Exception {

        assertThat(ingredient.toString(), matchesPattern("Ingredient\\{recipeId=[0-9a-f\\-]+, name='tomatoes', quantity=10, unit='items'\\}"));
    }

    @Test
    public void canSerialiseAndDeserialise() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(ingredient);

        Ingredient deserialised = objectMapper.readValue(json, Ingredient.class);

        assertThat(deserialised, equalTo(ingredient));
    }

    @Test
    public void canCompareIngredients() throws Exception {
        Ingredient copy = new Ingredient(ingredient.getRecipeId(), ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit());
        Ingredient differentId = new Ingredient(UUIDGen.getTimeUUID(), ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit());

        assertThat(copy, equalTo(ingredient));
        assertThat(differentId, not(equalTo(ingredient)));
    }
}
