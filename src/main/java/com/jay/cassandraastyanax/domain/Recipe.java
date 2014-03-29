package com.jay.cassandraastyanax.domain;

import org.apache.cassandra.utils.UUIDGen;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author jaycarey
 */
public class Recipe {

    private String name;

    private UUID id;

    public Recipe() {
        this (UUIDGen.getTimeUUID(), null);
    }

    public Recipe(String name) {
        this (UUIDGen.getTimeUUID(), name);
    }

    public Recipe(Recipe recipe) {
        this(recipe.id, recipe.name);
    }

    public Recipe(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (!id.equals(recipe.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        checkNotNull(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkNotNull(name);
        this.name = name;
    }
}
