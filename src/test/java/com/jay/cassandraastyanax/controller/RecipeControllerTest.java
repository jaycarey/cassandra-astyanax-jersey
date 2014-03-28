package com.jay.cassandraastyanax.controller;

import com.jay.cassandraastyanax.dao.IngredientDao;
import com.jay.cassandraastyanax.dao.RecipeDao;
import com.jay.cassandraastyanax.domain.Ingredient;
import com.jay.cassandraastyanax.domain.Recipe;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.LowLevelAppDescriptor;
import org.apache.cassandra.utils.UUIDGen;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static com.google.common.base.Optional.of;
import static java.util.Arrays.asList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeControllerTest extends JerseyTest {

    @Mock
    public RecipeDao mockRecipeDao;

    @Mock
    public IngredientDao mockIngredientDao;

    @Override
    protected AppDescriptor configure() {
        MockitoAnnotations.initMocks(this);

        RecipeController recipeController = new RecipeController(mockRecipeDao, mockIngredientDao);

        ResourceConfig resourceConfig = new DefaultResourceConfig();
        resourceConfig.getRootResourceSingletons().add(recipeController);
        resourceConfig.getSingletons().add(recipeController);
        resourceConfig.getFeatures().put("com.sun.jersey.api.json.POJOMappingFeature", true);

        return new LowLevelAppDescriptor.Builder(resourceConfig)
                .contextPath("")
                .build();
    }

    @Test
    public void canRetrieveRecipe() throws Exception {
        WebResource webResource = resource();
        UUID uuid = UUIDGen.getTimeUUID();

        when(mockRecipeDao.retrieve(uuid)).thenReturn(of(new Recipe(uuid, "Spaghetti Bolognaise")));
        when(mockIngredientDao.ingredientsForRecipe(uuid)).thenReturn(asList(
                new Ingredient(uuid, "tomatoes", 10, "items"),
                new Ingredient(uuid, "mince meat", 500, "grams")
        ));

        String responseMsg = webResource
                .path("recipe/" + uuid)
                .accept(APPLICATION_JSON_TYPE)
                .get(String.class);

        assertThat(responseMsg, equalTo(
                "{\"name\":\"Spaghetti Bolognaise\",\"id\":\"" + uuid + "\",\"ingredients\":[" +
                        "{\"recipeId\":\"" + uuid + "\",\"name\":\"tomatoes\",\"quantity\":10,\"unit\":\"items\"}," +
                        "{\"recipeId\":\"" + uuid + "\",\"name\":\"mince meat\",\"quantity\":500,\"unit\":\"grams\"}]}"));
    }

    @Test
    public void canAddRecipeAndIngredients() throws Exception {
        WebResource webResource = resource();
        UUID uuid = UUIDGen.getTimeUUID();

        String json = "{\"name\":\"Spaghetti Bolognaise\",\"id\":\"" + uuid + "\",\"ingredients\":[" +
                "{\"recipeId\":\"" + uuid + "\",\"name\":\"tomatoes\",\"quantity\":10,\"unit\":\"items\"}," +
                "{\"recipeId\":\"" + uuid + "\",\"name\":\"mince meat\",\"quantity\":500,\"unit\":\"grams\"}]}";

        webResource
                .path("recipe/" + uuid)
                .type(APPLICATION_JSON_TYPE)
                .put(json);

        verify(mockRecipeDao).persist(new Recipe(uuid, "Spaghetti Bolognaise"));
        verify(mockIngredientDao).persist(new Ingredient(uuid, "tomatoes", 10, "items"));
        verify(mockIngredientDao).persist(new Ingredient(uuid, "mince meat", 500, "grams"));
    }

    @Test
    public void testApplicationWadl() {
        WebResource webResource = resource();
        String serviceWadl = webResource.path("application.wadl").
                accept(MediaTypes.WADL).get(String.class);

        assertTrue(serviceWadl.length() > 0);
    }
}
