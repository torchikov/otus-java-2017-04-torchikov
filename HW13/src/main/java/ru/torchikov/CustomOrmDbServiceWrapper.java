package ru.torchikov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.torchikov.jdbc.cache.CacheEngine;
import ru.torchikov.jdbc.datasets.UserDataSet;
import ru.torchikov.jdbc.datasets.base.BaseDataSet;
import ru.torchikov.jdbc.dbservice.CustomOrmDBService;
import ru.torchikov.jdbc.dbservice.DBService;

import javax.annotation.PostConstruct;


/**
 * Created by Torchikov Sergei on 11.07.2017.
 * Wrapper of {@link CustomOrmDBService} with PostConstruct method and cache engine as constructor argument
 */
@Service(value = "dbService")
public class CustomOrmDbServiceWrapper implements DBService {

    private CustomOrmDBService dbService;

    @Autowired
    public CustomOrmDbServiceWrapper(CacheEngine<UserDataSet> cache, CustomOrmDBService dbService) {
        this.dbService = dbService;
        dbService.registerCache(cache);
    }

    @Override
    public <T extends BaseDataSet> long save(T dataSet) {
        return dbService.save(dataSet);
    }

    @Override
    public <T extends BaseDataSet> T getById(long id, Class<T> clazz) {
        return dbService.getById(id, clazz);
    }

    @Override
    public void shutdown() {
        dbService.shutdown();
    }

    @PostConstruct
    public void justForTest() throws Exception {
        TestHelper.dropTableUsers();
        TestHelper.createTableUsers();
        TestHelper.addUserToDb("Mike", 21);
        TestHelper.addUserToDb("Anna", 25);
        TestHelper.addUserToDb("John", 19);

        for (int i = 0; i < 10; i++) {
            dbService.getById(1L, UserDataSet.class);
            dbService.getById(2L, UserDataSet.class);
        }
    }
}
