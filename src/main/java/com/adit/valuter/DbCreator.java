package com.adit.valuter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbCreator {
    private static final String DB_PATH = "vaulter.db";

    public static void createDb(String pin) {
        String url = "jdbc:sqlite:" + DB_PATH;

        try (Connection conn = DriverManager.getConnection(url)) {
            try (Statement stmt = conn.createStatement()) {
                // Set encryption key (must be the first statement)
                stmt.execute("PRAGMA key = '" + pin + "';");

                // Create passwords table if it doesn't exist
                stmt.execute("CREATE TABLE IF NOT EXISTS passwords ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "platform TEXT NOT NULL, "
                        + "username TEXT NOT NULL, "
                        + "password TEXT NOT NULL, "
                        + "datetime TEXT DEFAULT (strftime('%d-%m-%Y, %H-%M', 'now', 'localtime'))"
                        + ");"
                );

                System.out.println("Encrypted database created successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error Setting Up DB: " + e.getMessage());
        }
    }
}
