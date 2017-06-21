package ru.torchikov.jdbc.dao;


import ru.torchikov.jdbc.datasets.UserDataSet;
import ru.torchikov.jdbc.datasets.base.BaseDataSet;

/**
 * Created by sergei on 19.06.17.
 */
public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private static final DAO<UserDataSet> userDao = new UserDAO();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public <T extends BaseDataSet> DAO<T> getDAO(Class<? extends BaseDataSet> clazz) {
        if (clazz.equals(UserDataSet.class)) {
            return (DAO<T>) userDao;
        }
        return null;
    }
}
