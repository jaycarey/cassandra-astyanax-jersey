package com.jay.cassandraastyanax.controller;

import com.google.common.base.Optional;
import com.jay.cassandraastyanax.KeyspaceFactory;
import com.jay.cassandraastyanax.dao.IngredientDao;
import com.jay.cassandraastyanax.dao.RecipeDao;
import com.jay.cassandraastyanax.domain.Ingredient;
import com.jay.cassandraastyanax.domain.Recipe;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import com.sun.jersey.spi.resource.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@Path("/recipe")
@Singleton
public class RecipeService {

    private final RecipeDao recipeDao;

    private final IngredientDao ingredientDao;

    public RecipeService() {
        this(new KeyspaceFactory());
    }

    private RecipeService(KeyspaceFactory keyspaceFactory) {
        this(new RecipeDao(keyspaceFactory), new IngredientDao(keyspaceFactory));
    }

    public RecipeService(RecipeDao recipeDao, IngredientDao ingredientDao) {
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
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