package ru.torchikov.jdbc.dao;

import ru.torchikov.jdbc.datasets.base.EntityColumnName;
import ru.torchikov.jdbc.datasets.base.EntityFieldName;
import ru.torchikov.jdbc.datasets.base.EntityHolder;
import ru.torchikov.jdbc.ReflectionHelper;
import ru.torchikov.jdbc.datasets.base.BaseDataSet;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * General interface for all DAO
 */
public interface DAO<T extends BaseDataSet> {
    Optional<T> get(long id, Class<T> entityClass);

    boolean save(T dataSet);

    default String getLoadSqlQuery(long id, Class<T> entityClass) {
        return "SELECT * FROM " +
                EntityHolder.getTableName(entityClass) +
                " WHERE id=" + id;
    }

    default String getSaveSqlQuery(T dataSet) throws IllegalAccessException, NoSuchFieldException {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(EntityHolder.getTableName(dataSet.getClass()));
        query.append("(");

        StringBuilder query2 = new StringBuilder(" VALUES(");

        Map<EntityFieldName, EntityColumnName> fieldToColumnMapping = EntityHolder.getFieldToColumnMapping(dataSet.getClass());
        int index = 1;
        int mapSize = fieldToColumnMapping.size();
        for (Map.Entry<EntityFieldName, EntityColumnName> entry :
                fieldToColumnMapping.entrySet()) {
            query.append(entry.getValue().getName());
            Object fieldValue = ReflectionHelper.getFieldValue(entry.getKey().getName(), dataSet);
            if (fieldValue instanceof CharSequence) {
                query2.append("'")
                        .append(fieldValue)
                        .append("'");
            } else {
                query2.append(fieldValue);
            }

            if (index != mapSize) {
                query.append(",");
                query2.append(",");
            }
            index++;
        }
        query.append(")");
        query2.append(")");
        query.append(query2.toString());
        return query.toString();
    }
}
