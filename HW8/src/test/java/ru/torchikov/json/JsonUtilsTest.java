package ru.torchikov.json;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Torchikov Sergei on 30.05.2017.
 * Test for @{@link JsonUtils}
 */
public class JsonUtilsTest {
	private Gson gson;
	private Person person;
	private Person gsonPerson;
	private String jsonString;

	@Before
	public void setUp() {
		person = new Person();
		person.setName("Peter");
		person.setAge(23);
		gson = new Gson();
	}

	@Test
	public void simpleTypes() throws Exception {
		jsonString = JsonUtils.toJson(person);
		gsonPerson = gson.fromJson(jsonString, Person.class);
		assertEquals(this.person, gsonPerson);
	}

	@Test
	public void withList() throws Exception {
		List<String> phones = Arrays.asList("+79200000001", "79200000002", "79200000003");
		person.setPhones(phones);
		jsonString = JsonUtils.toJson(person);
		gsonPerson = gson.fromJson(jsonString, Person.class);
		assertEquals(person, gsonPerson);
	}

	@Test
	public void withMap() throws Exception {
		Map<String, String> phoneToType = new HashMap<>();
		phoneToType.put("79200000001", "Home");
		phoneToType.put("79200000002", "Mobile");
		phoneToType.put("79200000003", "Work");
		person.setPhoneToType(phoneToType);
		jsonString = JsonUtils.toJson(person);
		gsonPerson = gson.fromJson(jsonString, Person.class);
		assertEquals(person, gsonPerson);
	}

	@Test
	public void withStringArray() throws Exception {
		String[] friends = new String[]{"Mary", "Tom", "John"};
		person.setFriends(friends);
		jsonString = JsonUtils.toJson(person);
		gsonPerson = gson.fromJson(jsonString, Person.class);
		assertEquals(person, gsonPerson);
	}

	@Test
	public void withIntArray() throws Exception {
		int[] intArray = new int[]{1, 2, 3, 4, 5};
		person.setIntArray(intArray);
		jsonString = JsonUtils.toJson(person);
		gsonPerson = gson.fromJson(jsonString, Person.class);
		assertEquals(person, gsonPerson);
	}

	@Test
	public void withMultiDimensionalArray() throws Exception {
		int[][] matrix = new int[3][3];
		for (int i = 0; i < matrix[0].length; i++) {
			for (int j = 0; j < matrix[1].length; j++) {
				matrix[i][j] = i * j;
			}
		}
		person.setMatrix(matrix);
		jsonString = JsonUtils.toJson(person);
		gsonPerson = gson.fromJson(jsonString, Person.class);
		assertEquals(person, gsonPerson);
	}

	@Test
	public void withAnotherReferenceType() throws Exception {
		Address address = new Address();
		address.setCity("Moscow");
		address.setStreet("Tverskaya");
		address.setHouse(1);
		address.setFlat(35);
		person.setAddress(address);
		jsonString = JsonUtils.toJson(person);
		gsonPerson = gson.fromJson(jsonString, Person.class);
		assertEquals(person, gsonPerson);
	}
}
