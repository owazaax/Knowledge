package com.redesky.knowledge.shared.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private static Connection connection;

    public static void openConnection(String hostname, int port, String database, String username, String password) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database, username, password);
    }

    public static void closeConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            return;
        }

        connection.close();
    }

    public static PreparedStatement prepareStatement(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            return null;
        }

        return connection.prepareStatement(query);
    }

    public static Connection getConnection() {
        return connection;
    }

}