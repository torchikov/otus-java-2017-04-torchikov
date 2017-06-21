package ru.torchikov.jdbc.dbservice;


import ru.torchikov.jdbc.ObjectNotFoundException;
import ru.torchikov.jdbc.cache.CacheEngine;
import ru.torchikov.jdbc.dao.DAO;
import ru.torchikov.jdbc.dao.DAOFactory;
import ru.torchikov.jdbc.datasets.base.BaseDataSet;

import java.util.Optional;

/**
 * Created by sergei on 19.06.17.
 */
public class CustomOrmDBService implements DBService {

    private CacheEngine<? super BaseDataSet> cache;


    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public <T extends BaseDataSet> long save(T dataSet) {
        DAO<T> dao = daoFactory.getDAO(dataSet.getClass());
        long id = dao.save(dataSet);
        dataSet.setId(id);
        return id;
    }

    @Override
    public <T extends BaseDataSet> T getById(long id, Class<T> clazz) {
        T cachedResult = getFromCache(id);
        if (cachedResult != null) {
            return cachedResult;
        }
        DAO<T> dao = daoFactory.getDAO(clazz);
        Optional<T> result = dao.get(id, clazz);
        if (cache != null) {
            result.ifPresent(entity -> cache.put(entity));
        }
        return result.orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerCache(CacheEngine<? extends BaseDataSet> cacheEngine) {
        cache = (CacheEngine<? super BaseDataSet>) cacheEngine;
    }

    @SuppressWarnings("unchecked")
    private <T extends BaseDataSet> T getFromCache(long id) {
        T cachedResult = null;
        if (cache != null) {
            cachedResult = (T) cache.get(id);
        }
        return cachedResult;
    }

    @Override
    public void shutdown() {

    }
}
