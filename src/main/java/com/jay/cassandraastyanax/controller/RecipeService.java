/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jay.cassandraastyanax.controller;

import com.google.common.base.Optional;
import com.jay.cassandraastyanax.KeyspaceFactory;
import com.jay.cassandraastyanax.dao.RecipeDao;
import com.jay.cassandraastyanax.domain.Recipe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@Path("/recipe")
public class RecipeService {

    private final RecipeDao recipeDao;

    public RecipeService() {
        recipeDao = new RecipeDao(new KeyspaceFactory());
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<ISP> getISPs() {
//        return recipeDao.;
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Recipe getRecipe(@PathParam("id") UUID id) {
        Optional<Recipe> retrieve = recipeDao.retrieve(id);
        System.out.println("Found: " + retrieve.orNull());
        return retrieve.orNull();
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