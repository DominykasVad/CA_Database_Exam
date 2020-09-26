package org.example.databaseManager;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static DatabaseManager databaseManager = null;
    private String databaseUrlTemplate;
    private String databaseHost;
    private String databasePort;
    private String databaseName;
    private String databaseUsername;
    private String databasePassword;

    private DatabaseManager() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        this.databaseUrlTemplate = properties.getProperty("urlTemplate");
        this.databaseHost = properties.getProperty("host");
        this.databasePort = properties.getProperty("port");
        this.databaseName = properties.getProperty("database");
        this.databaseUsername = properties.getProperty("username");
        this.databasePassword = properties.getProperty("password");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(String.format(databaseUrlTemplate,
                    databaseHost,
                    databasePort,
                    databaseName,
                    databaseUsername,
                    databasePassword));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static DatabaseManager getDatabaseManagerInstance() throws IOException {
        if (databaseManager == null)
            databaseManager = new DatabaseManager();
        return databaseManager;
    }

}
