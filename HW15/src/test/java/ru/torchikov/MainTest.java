package ru.torchikov;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.torchikov.base.DBService;
import ru.torchikov.configurations.ApplicationConfig;
import ru.torchikov.dataset.UserDataSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class MainTest {

	@Autowired
	private DBService dbService;

	@Test
	public void saveAndLoad() throws Exception {
		UserDataSet user = new UserDataSet("Mike", 25);
		Long id = dbService.save(user);
		assertNotNull(id);

		UserDataSet mike = dbService.getById(id);
		assertEquals("Mike", mike.getName());
		assertEquals(25, mike.getAge());
	}
}
