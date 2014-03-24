package com.jay.cassandraastyanax.dao;

import com.google.common.base.Optional;
import com.jay.cassandraastyanax.KeyspaceFactory;
import com.jay.cassandraastyanax.domain.Recipe;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;

import java.util.UUID;

/**
 * @author jaycarey
 */
public abstract class AbstractDao<T> {

    protected final Keyspace keyspace;

    public AbstractDao(KeyspaceFactory keyspaceFactory) {
        keyspace = keyspaceFactory.getKeyspace("recipe_book");
    }

    public Optional<T> single(Rows<UUID, String> result) {
        if (result.size() == 0) {
            return Optional.absent();
        }
        return Optional.of(parse(result.getRowByIndex(0)));
    }

    protected abstract T parse(Row<UUID, String> rowByIndex);
}
