package ru.torchikov.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * Provide a connection to the database
 */
public final class ConnectionHelper {
	private ConnectionHelper() {
	}

	public static Connection getConnection() {
		try {
			ResourceBundle dbResource = ResourceBundle.getBundle("db");
			String dbUrl = dbResource.getString("url");
			return DriverManager.getConnection(dbUrl);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
