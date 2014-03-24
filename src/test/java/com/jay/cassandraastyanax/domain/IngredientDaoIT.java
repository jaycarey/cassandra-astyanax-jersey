package com.jay.cassandraastyanax.domain;

import com.jay.cassandraastyanax.KeyspaceFactory;
import com.jay.cassandraastyanax.dao.IngredientDao;
import org.apache.cassandra.utils.UUIDGen;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.UUID;

import static com.jay.beanmatcher.BeanMatcher.comparesTo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class IngredientDaoIT {

    private IngredientDao ingredientDao;

    @Before
    public void before() throws Exception {
        ingredientDao = new IngredientDao(new KeyspaceFactory());

    }

    @Test
    public void canPersistAndRetrieveAndDeleteIngredient() throws Exception {
        UUID recipeId = UUIDGen.getTimeUUID();

        Ingredient ingredient = new Ingredient(recipeId, "Salmon", 1, "fish");

        // Persist it
        ingredientDao.persist(ingredient);

        // Find it.
        assertThat(ingredientDao.ingredientsForRecipe(recipeId).get(0), comparesTo(ingredient).onAllFields());

        // Delete it.
        ingredientDao.remove(ingredient.getRecipeId(), ingredient.getName());

        // Can't find it.
        assertThat(ingredientDao.ingredientsForRecipe(recipeId), equalTo(Collections.<Ingredient>emptyList()));
    }
}
