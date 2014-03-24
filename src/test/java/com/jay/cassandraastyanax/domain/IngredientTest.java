package com.jay.cassandraastyanax.domain;

import org.apache.cassandra.utils.UUIDGen;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.jay.cassandraastyanax.TestUtils.matchesPattern;
import static org.hamcrest.CoreMatchers.equalTo;
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

}
