package com.jay.cassandraastyanax.domain;

import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.UUIDSerializer;

import java.util.UUID;

public class ColumnsFamilies {

    private ColumnsFamilies() {
    }

    public static final ColumnFamily<UUID, String> CF_RECIPE =
            new ColumnFamily<UUID, String>(
                    "recipe",
                    UUIDSerializer.get(),
                    StringSerializer.get());
}
