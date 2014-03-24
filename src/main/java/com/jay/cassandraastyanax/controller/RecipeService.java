package com.jay.cassandraastyanax.controller;

import com.google.common.base.Optional;
import com.jay.cassandraastyanax.KeyspaceFactory;
import com.jay.cassandraastyanax.dao.IngredientDao;
import com.jay.cassandraastyanax.dao.RecipeDao;
import com.jay.cassandraastyanax.domain.Ingredient;
import com.jay.cassandraastyanax.domain.Recipe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@Path("/recipe")
public class RecipeService {

    private final RecipeDao recipeDao;

    private final IngredientDao ingredientDao;

    public RecipeService() {
        KeyspaceFactory keyspaceFactory = new KeyspaceFactory();
        recipeDao = new RecipeDao(keyspaceFactory);
        ingredientDao = new IngredientDao(keyspaceFactory);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Recipe getRecipe(@PathParam("id") UUID id) {
        Optional<Recipe> recipe = recipeDao.retrieve(id);
        List<Ingredient> ingredients = ingredientDao.ingredientsForRecipe(id);
        System.out.println("Found: " + recipe.orNull());
        return recipe.isPresent() ? new RecipeAndIngredients(recipe.get(), ingredients) : null;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRecipe(Recipe recipe) {
        checkNotNull(recipe);
        System.out.println("Creating Recipe: " + recipe.toString());
        return Response.ok().entity(recipe.getId()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeRecipe(@PathParam("id") UUID id) {
        System.out.println("Removing Recipe with key: " + id);
        recipeDao.remove(id);
        return Response.ok().build();
    }

}