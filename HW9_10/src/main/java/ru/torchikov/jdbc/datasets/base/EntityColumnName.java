package ru.torchikov.jdbc.datasets.base;

/**
 * Created by sergei on 17.06.17.
 * Column name holder
 */
public class EntityColumnName {
    private String name;

    EntityColumnName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
