package ru.torchikov.jdbc.datasets.base;


import ru.torchikov.jdbc.datasets.AddressDataSet;
import ru.torchikov.jdbc.datasets.PhoneDataSet;
import ru.torchikov.jdbc.datasets.UserDataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sergei on 17.06.17
 * Holder for all entities
 */
@SuppressWarnings("Duplicates")
public final class EntityHolder {
    private EntityHolder() {
    }

    private static final Map<Class<? extends BaseDataSet>, EntityTableName> entityToTableMap = new HashMap<>();
    private static final Map<Class<? extends BaseDataSet>, Map<EntityFieldName, EntityColumnName>> entityToFieldAndColumnMap = new HashMap<>();

    static {
        putEntity(UserDataSet.class);
        putEntity(AddressDataSet.class);
        putEntity(PhoneDataSet.class);
    }

    public static String getTableName(Class<? extends BaseDataSet> entityClass) {
        EntityTableName tableName = entityToTableMap.get(entityClass);
        if (tableName == null) {
            throw new IllegalArgumentException(entityClass.getName() + " isn't an entity or wasn't put to entity map");
        }
        return tableName.getName();
    }

    public static Map<EntityFieldName, EntityColumnName> getFieldToColumnMapping(Class<? extends BaseDataSet> entityClass) {
        Map<EntityFieldName, EntityColumnName> fieldToColumnMap = entityToFieldAndColumnMap.get(entityClass);
        if (fieldToColumnMap == null) {
            throw new IllegalArgumentException(entityClass.getName() + " isn't an entity or wasn't put to entity map");
        }
        return fieldToColumnMap;
    }

    public static Set<Class<? extends BaseDataSet>> getEntityClasses() {
        return entityToTableMap.keySet();
    }

    private static void putEntity(Class<? extends BaseDataSet> entityClass) {
        entityToTableMap.put(entityClass, getEntityTableName(entityClass));
        entityToFieldAndColumnMap.put(entityClass, getFieldToColumnMap(entityClass));
    }

    private static Map<EntityFieldName, EntityColumnName> getFieldToColumnMap(Class<? extends BaseDataSet> entityClass) {
        Map<EntityFieldName, EntityColumnName> fieldToColumnMap = new HashMap<>();
        for (Field field : entityClass.getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                EntityFieldName fieldName = new EntityFieldName(field.getName());
                EntityColumnName columnName = new EntityColumnName(column.name());
                fieldToColumnMap.put(fieldName, columnName);
            }
            field.setAccessible(accessible);
        }
        return fieldToColumnMap;
    }

    private static EntityTableName getEntityTableName(Class<? extends BaseDataSet> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("It isn't an entity");
        }

        if (!entityClass.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("Table name wasn't specified for class: " + entityClass.getName());
        }

        Table table = entityClass.getAnnotation(Table.class);
        return new EntityTableName(table.name());
    }
}
