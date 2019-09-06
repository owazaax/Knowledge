package com.redesky.knowledge.shared.api;

import com.redesky.knowledge.shared.database.Database;
import com.redesky.knowledge.shared.objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KnowledgeAPI {

    public static boolean existsPlayer(String name) {
        name = name.toLowerCase();
        boolean exists = false;

        try {
            PreparedStatement statement = Database.prepareStatement("SELECT * FROM knowledge WHERE NICK=?");
            statement.setString(1, name);

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                exists = true;
            }

            results.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return exists;
    }

    public static void createPlayer(String name) {
        name = name.toLowerCase();

        if (existsPlayer(name)) {
            return;
        }

        try {
            PreparedStatement statement = Database.prepareStatement("INSERT INTO knowledge (NICK, ENTRADAS, SAIDAS) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setInt(2, 0);
            statement.setInt(3, 0);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static User loadPlayer(String name) {
        name = name.toLowerCase();

        if (!existsPlayer(name)) {
            createPlayer(name);
        }

        User user = null;

        try {
            PreparedStatement statement = Database.prepareStatement("SELECT * FROM knowledge WHERE NICK=?");
            statement.setString(1, name);

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                user = new User(name, results.getInt("ENTRADAS"), results.getInt("SAIDAS"));
            }

            results.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return user;
    }

    public static void savePlayer(User user) {
        try {
            PreparedStatement statement = Database.prepareStatement("UPDATE knowledge SET ENTRADAS=?, SAIDAS=? WHERE NICK=?");
            statement.setInt(1, user.getJoined());
            statement.setInt(2, user.getLeft());
            statement.setString(3, user.getName().toLowerCase());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}