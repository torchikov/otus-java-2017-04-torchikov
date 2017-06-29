package ru.torchikov.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Torchikov Sergei on 07.06.2017.
 * Provide a connection to the database
 */
@SuppressWarnings("Duplicates")
public final class ConnectionHelper {
    private ConnectionHelper() {
    }

    public static Connection getConnection() {
        try {
            ResourceBundle dbResource = ResourceBundle.getBundle("db");
            String dbUrl = dbResource.getString("url");
            String username = dbResource.getString("username");
            String password = dbResource.getString("password");
            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
