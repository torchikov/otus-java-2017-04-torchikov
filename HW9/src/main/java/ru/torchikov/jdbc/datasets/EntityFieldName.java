package ru.torchikov.jdbc.datasets;

/**
 * Created by sergei on 17.06.17.
 * Field name holder
 */
public class EntityFieldName {
    private String name;

    EntityFieldName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
