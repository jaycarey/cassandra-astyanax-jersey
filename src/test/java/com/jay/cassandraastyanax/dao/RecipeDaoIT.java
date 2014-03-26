package com.jay.cassandraastyanax.dao;

import com.google.common.base.Optional;
import com.jay.cassandraastyanax.KeyspaceFactory;
import com.jay.cassandraastyanax.dao.RecipeDao;
import com.jay.cassandraastyanax.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static com.jay.beanmatcher.BeanMatcher.comparesTo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RecipeDaoIT {

    private RecipeDao recipeDao;

    @Before
    public void before() throws Exception {
        recipeDao = new RecipeDao(new KeyspaceFactory());
    }

    @Test
    public void canPersistAndRetrieveAndDeleteRecipe() throws Exception {
        Recipe recipe = new Recipe("Test Recipe");

        // Persist it
        recipeDao.persist(recipe);

        // Find it.
        assertThat(recipeDao.retrieve(recipe.getId()).get(), comparesTo(recipe).onAllFields());

        // Delete it.
        recipeDao.remove(recipe.getId());

        // Can't find it.
        assertThat(recipeDao.retrieve(recipe.getId()), equalTo(Optional.<Recipe>absent()));

    }
}
