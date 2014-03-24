package com.jay.cassandraastyanax.dao;

import com.google.common.base.Optional;
import com.jay.cassandraastyanax.KeyspaceFactory;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;

import java.util.ArrayList;
import java.util.List;
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

    public List<T> list(Rows<UUID,String> rows) {
        List<T> results = new ArrayList<>(rows.size());
        for (Row<UUID, String> row : rows) {
            results.add(parse(row));
        }
        return results;
    }

    protected abstract T parse(Row<UUID, String> rowByIndex);
}
