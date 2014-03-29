package com.jay.cassandraastyanax.controller;

import com.jay.cassandraastyanax.dao.IngredientDao;
import com.jay.cassandraastyanax.dao.RecipeDao;
import com.jay.cassandraastyanax.domain.Ingredient;
import com.jay.cassandraastyanax.domain.Recipe;
import org.apache.cassandra.utils.UUIDGen;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static com.google.common.base.Optional.of;
import static java.util.Arrays.asList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeControllerTest extends JerseyTest {

    @Mock
    public RecipeDao mockRecipeDao;

    @Mock
    public IngredientDao mockIngredientDao;

    @Override
    protected Application configure() {

        MockitoAnnotations.initMocks(this);

        RecipeController recipeController = new RecipeController(mockRecipeDao, mockIngredientDao);

        RecipeResourceConfig recipeResourceConfig = new RecipeResourceConfig();
        recipeResourceConfig.register(recipeController);
        return recipeResourceConfig;
    }

    @Test
    public void canRetrieveRecipe() throws Exception {
        UUID uuid = UUIDGen.getTimeUUID();

        when(mockRecipeDao.retrieve(uuid)).thenReturn(of(new Recipe(uuid, "Spaghetti Bolognaise")));
        when(mockIngredientDao.ingredientsForRecipe(uuid)).thenReturn(asList(
                new Ingredient(uuid, "tomatoes", 10, "items"),
                new Ingredient(uuid, "mince meat", 500, "grams")
        ));

        String responseMsg = target("recipe/" + uuid)
                .request()
                .accept(APPLICATION_JSON_TYPE)
                .get(String.class);

        assertThat(responseMsg, equalTo(
                "{\"recipe\":{\"name\":\"Spaghetti Bolognaise\",\"id\":\"" + uuid + "\"},\"ingredients\":[" +
                        "{\"recipeId\":\"" + uuid + "\",\"name\":\"tomatoes\",\"quantity\":10,\"unit\":\"items\"}," +
                        "{\"recipeId\":\"" + uuid + "\",\"name\":\"mince meat\",\"quantity\":500,\"unit\":\"grams\"}]}"));
    }

    @Test
    public void canAddRecipeAndIngredients() throws Exception {
        UUID uuid = UUIDGen.getTimeUUID();

        String json = "{\"recipe\":{\"name\":\"Spaghetti Bolognaise\",\"id\":\"" + uuid + "\"},\"ingredients\":[" +
                "{\"recipeId\":\"" + uuid + "\",\"name\":\"tomatoes\",\"quantity\":10,\"unit\":\"items\"}," +
                "{\"recipeId\":\"" + uuid + "\",\"name\":\"mince meat\",\"quantity\":500,\"unit\":\"grams\"}]}";

        Response result = target("recipe/" + uuid)
                .request()
                .accept(APPLICATION_JSON_TYPE)
                .put(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));

        assertThat(result.getStatusInfo().getFamily(), equalTo(SUCCESSFUL));
        verify(mockRecipeDao).persist(new Recipe(uuid, "Spaghetti Bolognaise"));
        verify(mockIngredientDao).persist(new Ingredient(uuid, "tomatoes", 10, "items"));
        verify(mockIngredientDao).persist(new Ingredient(uuid, "mince meat", 500, "grams"));
    }

    @Test
    public void testApplicationWadl() {
        String serviceWadl = target("application.wadl")
                .request()
                .accept(MediaTypes.WADL)
                .get(String.class);

        assertTrue(serviceWadl.length() > 0);
    }
}
