package ru.torchikov.jdbc.datasets;

/**
 * Created by sergei on 17.06.17.
 * Table name holder
 */
class EntityTableName {
    private String name;

    EntityTableName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
