package ru.torchikov.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.torchikov.jdbc.datasets.user.UserDataSet;
import ru.torchikov.jdbc.dbservice.DBService;
import ru.torchikov.jdbc.dbservice.HiberanteDBService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by sergei on 19.06.17.
 */
public class HibernateTest {
    private DBService dbService;

    @Before
    public void setUp() {
        dbService = new HiberanteDBService();
    }

    @After
    public void tearDown() {
        if (dbService != null) {
            dbService.shutdown();
        }
    }

    @Test
    public void save() {
        UserDataSet user = new UserDataSet("Mike", 21);
        boolean result = dbService.save(user);
        assertTrue(result);
    }

    @Test
    public void load() {
        dbService.save(new UserDataSet("Mike", 21));
        UserDataSet user = dbService.getById(1L, UserDataSet.class);
        assertEquals(1L, user.getId());
        assertEquals("Mike", user.getName());
        assertEquals(21, user.getAge());
    }
}
