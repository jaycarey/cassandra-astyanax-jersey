package com.jay.cassandraastyanax.domain;

import java.util.UUID;

/**
 * @author jaycarey
 */
public class Ingredient {

    private UUID recipeId;

    private String name;

    private int quantity;

    private String unit;

    public Ingredient(UUID recipeId) {
        this.recipeId = recipeId;
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
    public String toString() {
        return "Ingredient{" +
                "recipeId=" + recipeId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
