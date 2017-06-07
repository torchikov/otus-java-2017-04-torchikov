package ru.torchikov.jdbc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * General interface for all DAO
 */
public interface DAO<T extends BaseDataSet> {
	Optional<T> get(long id, Class<T> entityClass) throws SQLException, IllegalAccessException, InstantiationException;

    boolean save(T dataSet) throws SQLException;

    default String getLoadSqlQuery(long id, Class<T> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("It isn't an entity");
        }

        if (!entityClass.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("Table name wasn't specified");
        }
        Table table = entityClass.getAnnotation(Table.class);
        StringBuilder sb = new StringBuilder("SELECT * FROM ")
                .append(table.name())
                .append(" WHERE id=")
                .append(id);
        return sb.toString();

    }

	default String getSaveSqlQuery(T dataSet) throws IllegalAccessException {
        Map<String, Object> nameToValue = new HashMap<>();
        StringBuilder firstPart = new StringBuilder("INSERT INTO ");
        StringBuilder secondPart = new StringBuilder(" VALUES(");
        if (!dataSet.getClass().isAnnotationPresent(Entity.class)) {
			throw new RuntimeException("It isn't an entity");
		}
		if (!dataSet.getClass().isAnnotationPresent(Table.class)) {
			throw new RuntimeException("You must specify a table name");
		}
		String tableName = dataSet.getClass().getAnnotation(Table.class).name();
		firstPart.append(tableName);
        firstPart.append("(");
        for (Field field : dataSet.getClass().getDeclaredFields()) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (Objects.isNull(column)) {
                continue;
            }
            Object fieldValue = field.get(dataSet);
            if (fieldValue.getClass().getSimpleName().equals("String")) {
                String result = "'" + fieldValue + "'";
                nameToValue.put(column.name(), result);
            } else {
                nameToValue.put(column.name(), fieldValue);
            }
            field.setAccessible(accessible);
        }
        int mapSize = nameToValue.size();
        int index = 1;
        for (Map.Entry<String, Object> entry : nameToValue.entrySet()) {
            firstPart.append(entry.getKey());
            secondPart.append(entry.getValue());
            if (index != mapSize) {
                firstPart.append(",");
                secondPart.append(",");
            }
            index++;
        }
        firstPart.append(")");
        secondPart.append(")");
        firstPart.append(secondPart.toString());
        return firstPart.toString();
    }
}
