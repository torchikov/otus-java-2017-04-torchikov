package ru.torchikov.jdbc.dbservice;

import ru.torchikov.jdbc.datasets.base.BaseDataSet;

/**
 * Created by sergei on 19.06.17.
 *
 */
public interface DBService {
    <T extends BaseDataSet> boolean save(T dataSet);

    <T extends BaseDataSet> T getById(long id, Class<T> clazz);

    void shutdown();
}
