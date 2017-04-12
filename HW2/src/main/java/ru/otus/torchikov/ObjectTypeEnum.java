package ru.otus.torchikov;

/**
 * Created by Torchikov Sergei on 12.04.2017.
 */
public enum ObjectTypeEnum {
    STRING_WITH_STRING_POOL(String.class.getName() + " with string pool"),
    STRING_WITHOUT_STRING_POOL(String.class.getName() + " without string pool"),
    OBJECT(Object.class.getName()),
    INTEGER(Integer.class.getName()),
    ;

    private String description;

    ObjectTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

