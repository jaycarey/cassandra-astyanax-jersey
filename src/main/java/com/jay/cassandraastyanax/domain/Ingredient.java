package com.jay.cassandraastyanax.domain;

import java.util.UUID;

/**
 * @author jaycarey
 */
public class Ingredient {

    private final UUID recipeId;

    private final String name;

    private final int quantity;

    private final String unit;

    /**
     * Default constructor required by jackson.
     */
    public Ingredient() {
        this (null, null, -1, null);
    }

    public Ingredient(UUID recipeId, String name, int quantity, String unit) {
        this.recipeId = recipeId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (!name.equals(that.name)) return false;
        if (!recipeId.equals(that.recipeId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recipeId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "recipeId=" + recipeId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
