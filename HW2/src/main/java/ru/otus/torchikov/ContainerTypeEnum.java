package ru.otus.torchikov;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by Torchikov Sergei on 12.04.2017.
 */
public enum ContainerTypeEnum {
    ARRAY("array"),
    ARRAY_LIST(ArrayList.class.getName()),
    HASH_SET(HashSet.class.getName()),
    LINKED_LIST(LinkedList.class.getName())
    ;

    private String description;

    ContainerTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


