package ru.torchikov.jdbc;

import ru.torchikov.jdbc.datasets.AddressDataSet;
import ru.torchikov.jdbc.datasets.PhoneDataSet;
import ru.torchikov.jdbc.datasets.UserDataSet;
import ru.torchikov.jdbc.dbservice.DBService;
import ru.torchikov.jdbc.dbservice.HiberanteDBService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by sergei on 19.06.17.
 */
@SuppressWarnings("Duplicates")
public class HibernateTest {
    private DBService dbService;
    private UserDataSet user;

    @Before
    public void setUp() {
        dbService = new HiberanteDBService();
        this.user = new UserDataSet("Mike", 21);
        AddressDataSet address = new AddressDataSet("Tverskaya", 123456);
        List<PhoneDataSet> phones = Arrays.asList(new PhoneDataSet(7, "9201112233"),
                new PhoneDataSet(7, "9210001122"));
        user.setAddress(address);
        user.setPhones(phones);
    }

    @After
    public void tearDown() {
        if (dbService != null) {
            dbService.shutdown();
        }
    }

    @Test
    public void save() {
        long id = dbService.save(user);
        assertEquals(1L, id);
    }

    @Test
    public void load() {
        dbService.save(user);
        UserDataSet user = dbService.getById(1L, UserDataSet.class);
        assertEquals(1L, user.getId());
        assertEquals("Mike", user.getName());
        assertEquals(21, user.getAge());
        AddressDataSet address = user.getAddress();
        assertEquals("Tverskaya", address.getStreet());
        assertEquals(123456, address.getZip());
        List<PhoneDataSet> phones = user.getPhones();
        assertEquals(2, phones.size());
    }

    @Test
    public void cacheTest() {
        long id = dbService.save(user);
        assertEquals(1L, id);
        UserDataSet user = dbService.getById(1L, UserDataSet.class);
        assertEquals("Mike", user.getName());
        user = dbService.getById(1L, UserDataSet.class);
        assertEquals("Mike", user.getName());
    }
}
