package ru.torchikov.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.torchikov.jdbc.dao.DAO;
import ru.torchikov.jdbc.dao.UserDAO;
import ru.torchikov.jdbc.datasets.user.UserDataSet;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * Tests
 */
public class BaseTest {
	private DAO<UserDataSet> dao;

	@Before
	public void setUp() throws Exception {
		dao = new UserDAO();
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
        UserDataSet user = new UserDataSet();
        user.setName("Mike");
        user.setAge(21);
        assertTrue(dao.save(user));
	}

	@Test
	public void get() throws Exception {
		Optional<UserDataSet> userDataSet = dao.get(1L, UserDataSet.class);
		assertTrue(userDataSet.isPresent());
		UserDataSet user = userDataSet.get();
		assertEquals("Anna", user.getName());
		assertEquals(19, user.getAge());
	}

	@Test
	public void saveAndLoad() throws Exception {
		UserDataSet user = new UserDataSet();
        user.setName("Mike");
        user.setAge(21);
        assertTrue(dao.save(user));
		Optional<UserDataSet> result = dao.get(3L, UserDataSet.class);
		assertTrue(result.isPresent());
		UserDataSet mike = result.get();
		assertEquals(user.getName(), mike.getName());
		assertEquals(user.getAge(), mike.getAge());
	}

	@Test
	public void getWithNoExistId() throws Exception {
		Optional<UserDataSet> result = dao.get(3L, UserDataSet.class);
		assertFalse(result.isPresent());
	}

	@Test
	public void saveTwoEntities() throws Exception {
		UserDataSet mike = new UserDataSet();
		mike.setName("Mike");
		mike.setAge(21);
		assertTrue(dao.save(mike));

		UserDataSet anna = new UserDataSet();
		anna.setName("Anna");
		anna.setAge(20);
		assertTrue(dao.save(anna));
	}
}
