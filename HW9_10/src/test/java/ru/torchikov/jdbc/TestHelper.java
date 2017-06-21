package ru.torchikov.jdbc;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * Create test data
 */
final class TestHelper {
	private TestHelper() {
	}

	static void createUserTable() throws Exception {
		String query = "CREATE TABLE users (id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
				"name VARCHAR(255) DEFAULT NULL, " +
				"age INTEGER DEFAULT NULL)";
		execute(query);
	}

	static void dropUserTable() throws Exception {
		String query = "DROP TABLE users";
		execute(query);
	}

	static void addUserToDb(String name, int age) throws Exception {
		String query = "INSERT INTO users(name, age) VALUES ('" + name + "', " + age + ")";
		execute(query);

	}

	private static void execute(String query) throws Exception {
		try (Connection connection = ConnectionHelper.getConnection()) {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
	}

}
