package ru.torchikov.base;


import ru.torchikov.dataset.UserDataSet;

/**
 * Created by sergei on 19.06.17.
 *
 */
public interface DBService {
    UserDataSet getById(long id);

    Long save(UserDataSet user);

    void init();
}
