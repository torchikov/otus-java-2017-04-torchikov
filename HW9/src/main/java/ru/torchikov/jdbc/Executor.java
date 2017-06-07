package ru.torchikov.jdbc;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * Executor for SQL queries
 */
public class Executor<T extends BaseDataSet> {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public Optional<T> executeGet(String query, Class<T> entityClass) throws SQLException, IllegalAccessException, InstantiationException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return Optional.ofNullable(getResultSet(entityClass, resultSet));

    }

    public int executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }

    private T getResultSet(Class<T> entityClass, ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException {
        if (resultSet.next()) {
            T result = entityClass.newInstance();
            for (Field field : entityClass.getDeclaredFields()) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    field.set(result, resultSet.getObject(column.name()));
                }
                field.setAccessible(accessible);
            }
            result.setId(resultSet.getLong("id"));
            return result;
        } else {
            return null;
        }
    }

}
