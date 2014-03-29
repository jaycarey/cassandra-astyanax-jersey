package com.jay.cassandraastyanax.domain;

import com.jay.cassandraastyanax.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static com.jay.cassandraastyanax.TestUtils.matchesPattern;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author jaycarey
 */
public class RecipeTest {

    private Recipe recipe;

    @Before
    public void before() throws Exception {
        recipe = new Recipe("Test Recipe");
    }

    @Test
    public void newRecipeHasCorrectValues() throws Exception {
        assertThat(recipe.getId(), notNullValue());
        assertThat(recipe.getName(), equalTo("Test Recipe"));
    }

    @Test
    public void recipesCanBeCopied() throws Exception {
        Recipe copy = new Recipe(recipe);

        assertThat(recipe.getId(), equalTo(copy.getId()));
        assertThat(recipe.getName(), equalTo(copy.getName()));
    }

    @Test
    public void recipesCanBeCompared() throws Exception {
        Recipe copy = new Recipe(recipe);
        Recipe different = new Recipe();

        assertThat(recipe, equalTo(copy));
        assertThat(recipe, not(equalTo(different)));
    }

    @Test
    public void producesHumanReadableString() throws Exception {

            assertThat(recipe.toString(), matchesPattern("Recipe\\{name='Test Recipe', id=[0-9a-f\\-]+\\}"));
    }
}
