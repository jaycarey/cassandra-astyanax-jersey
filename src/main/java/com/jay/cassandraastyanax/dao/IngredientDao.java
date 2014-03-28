package com.jay.cassandraastyanax.dao;

import com.jay.cassandraastyanax.domain.KeyspaceFactory;
import com.jay.cassandraastyanax.domain.Ingredient;
import com.jay.cassandraastyanax.exception.SystemException;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;

import java.util.List;
import java.util.UUID;

import static com.jay.cassandraastyanax.domain.ColumnsFamilies.CF_INGREDIENT_BY_RECIPE;
import static java.lang.String.format;

/**
 * @author jaycarey
 */
public class IngredientDao extends AbstractDao<Ingredient> {

    public static final String INSERT = "INSERT INTO ingredient_by_recipe (recipe_id, name, quantity, unit) VALUES (?, ?, ?, ?);";

    public static final String DELETE = "DELETE FROM ingredient_by_recipe WHERE recipe_id = ? AND name = ?;";

    public static final String SELECT_FOR_INGREDIENT_BY_RECIPE = "SELECT * FROM ingredient_by_recipe WHERE recipe_id = ?;";

    public IngredientDao(KeyspaceFactory keyspaceFactory) {
        super(keyspaceFactory);
    }

    public void persist(Ingredient ingredient) {

        try {
            keyspace.prepareQuery(CF_INGREDIENT_BY_RECIPE)
                    .withCql(INSERT)
                    .asPreparedStatement()
                    .withUUIDValue(ingredient.getRecipeId())
                    .withStringValue(ingredient.getName())
                    .withIntegerValue(ingredient.getQuantity())
                    .withStringValue(ingredient.getUnit())
                    .execute();
        } catch (ConnectionException e) {
            throw new SystemException(format("Unable to insert ingredient [%s]", ingredient), e);
        }
    }

    public List<Ingredient> ingredientsForRecipe(UUID recipeId) {

        try {
            Rows<UUID, String> result = keyspace.prepareQuery(CF_INGREDIENT_BY_RECIPE)
                    .withCql(SELECT_FOR_INGREDIENT_BY_RECIPE)
                    .asPreparedStatement()
                    .withUUIDValue(recipeId)
                    .execute()
                    .getResult()
                    .getRows();

            return list(result);

        } catch (ConnectionException e) {
            throw new SystemException(format("Unable to fetch ingredient with id [%s]", recipeId), e);
        }

    }

    public void remove(UUID recipe_id, String name) {

        try {
            keyspace.prepareQuery(CF_INGREDIENT_BY_RECIPE)
                    .withCql(DELETE)
                    .asPreparedStatement()
                    .withUUIDValue(recipe_id)
                    .withStringValue(name)
                    .execute();

        } catch (ConnectionException e) {
            throw new SystemException(format("Unable to delete ingredient with id [%s], name [%s]", recipe_id, name), e);
        }
    }

    protected Ingredient parse(Row<UUID, String> rowByIndex) {
        return new Ingredient(
                rowByIndex.getColumns().getUUIDValue("recipe_id", null),
                rowByIndex.getColumns().getStringValue("name", null),
                rowByIndex.getColumns().getIntegerValue("quantity", null),
                rowByIndex.getColumns().getStringValue("unit", null)
        );
    }
}
