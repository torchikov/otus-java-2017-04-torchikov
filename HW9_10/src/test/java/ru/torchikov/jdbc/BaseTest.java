package ru.torchikov.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.torchikov.jdbc.datasets.UserDataSet;
import ru.torchikov.jdbc.dbservice.CustomOrmDBService;
import ru.torchikov.jdbc.dbservice.DBService;

import static org.junit.Assert.*;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * Tests
 */
public class BaseTest {
	private DBService dbService;

	@Before
	public void setUp() throws Exception {
		dbService = new CustomOrmDBService();
		TestHelper.createUserTable();
		TestHelper.addUserToDb("Anna", 19);
		TestHelper.addUserToDb("Peter", 27);
	}

	@After
	public void tearDown() throws Exception {
		TestHelper.dropUserTable();
	}

	@Test
	public void save() throws Exception {
        UserDataSet user = new UserDataSet("Mike", 21);
        assertTrue(dbService.save(user));
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
        assertTrue(dbService.save(user));
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
		assertTrue(dbService.save(mike));

		UserDataSet anna = new UserDataSet("Anna", 21);
		assertTrue(dbService.save(anna));
	}
}
