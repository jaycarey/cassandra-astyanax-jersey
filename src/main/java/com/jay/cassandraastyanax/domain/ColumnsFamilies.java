package com.jay.cassandraastyanax.domain;

import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.UUIDSerializer;

import java.util.UUID;

public class ColumnsFamilies {

    public static final ColumnFamily<UUID, String> CF_RECIPE =
            new ColumnFamily<>(
                    "recipe",
                    UUIDSerializer.get(),
                    StringSerializer.get());

    public static final ColumnFamily<UUID, String> CF_INGREDIENT_BY_RECIPE =
            new ColumnFamily<>(
                    "ingredient_by_recipe",
                    UUIDSerializer.get(),
                    StringSerializer.get());
}
