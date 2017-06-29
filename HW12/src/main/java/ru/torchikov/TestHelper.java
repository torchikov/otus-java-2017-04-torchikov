package ru.torchikov;


import ru.torchikov.jdbc.ConnectionHelper;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * Create test data
 */
final class TestHelper {
	private TestHelper() {
	}

	static void createTableUsers() throws Exception {
		String query = "create table users(" +
				"ID bigint not null auto_increment," +
				" NAME varchar(20) not null," +
				" AGE int(3) not null, " +
				" primary key (ID))";
		execute(query);
	}

	static void dropRableUsers() throws Exception {
		String query = "drop table if exists users";
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
