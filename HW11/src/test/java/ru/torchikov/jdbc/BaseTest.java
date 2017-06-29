package ru.torchikov.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import ru.torchikov.jdbc.cache.CacheEngine;
import ru.torchikov.jdbc.cache.CustomCacheEngine;
import ru.torchikov.jdbc.datasets.UserDataSet;
import ru.torchikov.jdbc.dbservice.CustomOrmDBService;
import ru.torchikov.jdbc.dbservice.DBService;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * Tests
 */
@SuppressWarnings("Duplicates")
public class BaseTest {
    private DBService dbService;

    @Before
    public void setUp() throws Exception {
        dbService = new CustomOrmDBService();
        TestHelper.createTableUsers();
        TestHelper.addUserToDb("Anna", 19);
        TestHelper.addUserToDb("Peter", 27);
    }

    @After
    public void tearDown() throws Exception {
        TestHelper.dropRableUsers();
    }

    @Test
    public void save() throws Exception {
        UserDataSet user = new UserDataSet("Mike", 21);
        dbService.save(user);
        assertEquals(3L, user.getId());
    }

    @Test
    public void get() throws Exception {
        UserDataSet user = dbService.getById(1L, UserDataSet.class);
        assertNotNull(user);
        assertEquals("Anna", user.getName());
        assertEquals(19, user.getAge());
    }

    @Test
    public void saveAndLoad() throws Exception {
        UserDataSet user = new UserDataSet("Mike", 21);
        dbService.save(user);
        assertEquals(3L, user.getId());
        UserDataSet result = dbService.getById(3L, UserDataSet.class);
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getAge(), result.getAge());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void getWithNoExistId() throws Exception {
        dbService.getById(3L, UserDataSet.class);
    }

    @Test
    public void saveTwoEntities() throws Exception {
        UserDataSet mike = new UserDataSet("Mike", 21);
        dbService.save(mike);
        assertEquals(3L, mike.getId());

        UserDataSet anna = new UserDataSet("Anna", 21);
        dbService.save(anna);
        assertEquals(4L, anna.getId());
    }

    @Test
    public void getWithCache() {
        CacheEngine<UserDataSet> cacheEngine = spy(new CustomCacheEngine(10,
                TimeUnit.MINUTES.toMillis(5), 0, false));
        dbService.registerCache(cacheEngine);
        UserDataSet mike = new UserDataSet("Mike", 21);
        dbService.save(mike);
        assertEquals(3L, mike.getId());
        dbService.getById(3L, UserDataSet.class);
        verify(cacheEngine, times(1)).get(ArgumentMatchers.anyLong());
        assertEquals(1, cacheEngine.getMissCount());
        dbService.getById(3L, UserDataSet.class);
        assertEquals(1, cacheEngine.getHitCount());
    }

}
