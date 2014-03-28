package com.jay.cassandraastyanax.dao;

import com.google.common.base.Optional;
import com.jay.cassandraastyanax.domain.KeyspaceFactory;
import com.jay.cassandraastyanax.domain.Recipe;
import com.jay.cassandraastyanax.exception.SystemException;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;

import java.util.UUID;

import static com.jay.cassandraastyanax.domain.ColumnsFamilies.CF_RECIPE;
import static java.lang.String.format;

/**
 * @author jaycarey
 */
public class RecipeDao extends AbstractDao<Recipe> {

    public static final String INSERT = "INSERT INTO recipe (id, name) VALUES (?, ?);";

    public RecipeDao(KeyspaceFactory keyspaceFactory) {
        super(keyspaceFactory);
    }

    public void persist(Recipe recipe) {

        try {
            keyspace.prepareQuery(CF_RECIPE)
                    .withCql(INSERT)
                    .asPreparedStatement()
                    .withUUIDValue(recipe.getId())
                    .withStringValue(recipe.getName())
                    .execute();
        } catch (ConnectionException e) {
            throw new SystemException(format("Unable to insert recipe [%s]", recipe), e);
        }
    }

    public Optional<Recipe> retrieve(UUID id) {

        try {
            Rows<UUID, String> result = keyspace.prepareQuery(CF_RECIPE)
                    .withCql(format("SELECT * FROM recipe WHERE id=%s;", id))
                    .execute()
                    .getResult()
                    .getRows();

            return single(result);

        } catch (ConnectionException e) {
            throw new SystemException(format("Unable to fetch recipe with id [%s]", id), e);
        }

    }

    public void remove(UUID id) {

        try {
            keyspace.prepareQuery(CF_RECIPE)
                    .withCql(format("DELETE FROM recipe WHERE id=%s;", id))
                    .execute();

        } catch (ConnectionException e) {
            throw new SystemException(format("Unable to delete recipe with id [%s]", id), e);
        }
    }

    protected Recipe parse(Row<UUID, String> rowByIndex) {
        return new Recipe(
                rowByIndex.getColumns().getUUIDValue("id", null),
                rowByIndex.getColumns().getStringValue("name", null)
        );
    }
}
