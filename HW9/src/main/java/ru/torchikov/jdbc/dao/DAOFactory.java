package ru.torchikov.jdbc.dao;

import ru.torchikov.jdbc.datasets.BaseDataSet;
import ru.torchikov.jdbc.datasets.user.UserDataSet;

/**
 * Created by sergei on 19.06.17.
 */
public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public <T extends BaseDataSet> DAO<T> getDAO(Class<? extends BaseDataSet> clazz) {
        DAO<T> dao = null;
        if (clazz.equals(UserDataSet.class)) {
            dao = (DAO<T>) new UserDAO();
        }
        return dao;
    }
}
