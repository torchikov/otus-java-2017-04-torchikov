package ru.torchikov.jdbc;

import ru.torchikov.jdbc.datasets.base.BaseDataSet;
import ru.torchikov.jdbc.datasets.base.EntityColumnName;
import ru.torchikov.jdbc.datasets.base.EntityFieldName;
import ru.torchikov.jdbc.datasets.base.EntityHolder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
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

    public Optional<T> executeGet(String query, Class<T> entityClass) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return Optional.ofNullable(constructFromResultSet(entityClass, resultSet));

    }

    public int executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }

    private T constructFromResultSet(Class<T> entityClass, ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        if (resultSet.next()) {
            T result = entityClass.newInstance();
            Map<EntityFieldName, EntityColumnName> fieldToColumnMapping = EntityHolder.getFieldToColumnMapping(entityClass);

            for (Map.Entry<EntityFieldName, EntityColumnName> entry : fieldToColumnMapping.entrySet()) {
                Object value = resultSet.getObject(entry.getValue().getName());
                ReflectionHelper.setFieldValue(entry.getKey().getName(), value, result);
            }
            result.setId(resultSet.getLong("id"));
            return result;
        } else {
            return null;
        }
    }

}
