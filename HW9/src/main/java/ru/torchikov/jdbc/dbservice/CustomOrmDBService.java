package ru.torchikov.jdbc.dbservice;

import ru.torchikov.jdbc.ObjectNotFoundException;
import ru.torchikov.jdbc.dao.DAO;
import ru.torchikov.jdbc.dao.DAOFactory;
import ru.torchikov.jdbc.datasets.BaseDataSet;

import java.util.Optional;

/**
 * Created by sergei on 19.06.17.
 */
public class CustomOrmDBService implements DBService{
    private DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public <T extends BaseDataSet> boolean save(T dataSet) {
        DAO<T> dao = daoFactory.getDAO(dataSet.getClass());
        return dao.save(dataSet);
    }

    @Override
    public <T extends BaseDataSet> T getById(long id, Class<T> clazz) {
        DAO<T> dao = daoFactory.getDAO(clazz);
        Optional<T> result = dao.get(id, clazz);
        return result.orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public void shutdown() {

    }
}
