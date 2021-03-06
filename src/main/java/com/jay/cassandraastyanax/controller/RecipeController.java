package com.jay.cassandraastyanax.controller;

import com.google.common.base.Optional;
import com.jay.cassandraastyanax.dao.IngredientDao;
import com.jay.cassandraastyanax.dao.RecipeDao;
import com.jay.cassandraastyanax.domain.Ingredient;
import com.jay.cassandraastyanax.domain.KeyspaceFactory;
import com.jay.cassandraastyanax.domain.Recipe;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.*;

@Path("/recipe")
@Singleton
public class RecipeController {

    private final RecipeDao recipeDao;

    private final IngredientDao ingredientDao;

    public RecipeController() {
        this(new KeyspaceFactory());
    }

    private RecipeController(KeyspaceFactory keyspaceFactory) {
        this(new RecipeDao(keyspaceFactory), new IngredientDao(keyspaceFactory));
    }

    public RecipeController(RecipeDao recipeDao, IngredientDao ingredientDao) {
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
    }

    @GET
    @Path("/{id}")
    @Produces(TEXT_HTML)
    public Viewable getRecipeView(@PathParam("id") UUID id) {
        return new Viewable("/index", getRecipe(id));
    }

    @GET
    @Path("/{id}")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public RecipeAndIngredients getRecipe(@PathParam("id") UUID id) {
        Optional<Recipe> recipe = recipeDao.retrieve(id);
        List<Ingredient> ingredients = ingredientDao.ingredientsForRecipe(id);
        return recipe.isPresent() ? new RecipeAndIngredients(recipe.get(), ingredients) : null;
    }

    @PUT
    @Path("/{id}")
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    public Response addRecipe(RecipeAndIngredients recipeAndIngredients) {
        checkNotNull(recipeAndIngredients);
        recipeDao.persist(recipeAndIngredients.getRecipe());
        for (Ingredient ingredient : recipeAndIngredients.getIngredients()) {
            ingredientDao.persist(ingredient);
        }
        return Response.ok().entity(recipeAndIngredients.getRecipe().getId()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeRecipe(@PathParam("id") UUID id) {
        System.out.println("Removing Recipe with key: " + id);
        recipeDao.remove(id);
        return Response.ok().build();
    }

}
