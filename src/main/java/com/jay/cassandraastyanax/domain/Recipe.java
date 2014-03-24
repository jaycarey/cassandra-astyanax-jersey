package com.jay.cassandraastyanax.domain;

import org.apache.cassandra.utils.UUIDGen;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

/**
 * @author jaycarey
 */
@XmlRootElement
public class Recipe {

    private String name;

    private UUID id;

    public Recipe() {
    }

    public Recipe(String name) {
        this.name = name;
        id = UUIDGen.getTimeUUID();
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

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
