package org.example.application.loginHandler;

import org.example.databaseManager.DatabaseManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.sqlStatementManager.SelectStatements.SELECT_USER_LOGIN;

public class LoginHandler {
    private DatabaseManager databaseManager;

    public LoginHandler() throws IOException {
        this.databaseManager = DatabaseManager.getDatabaseManagerInstance();
    }

    public Integer handle(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        Integer userId = null;
        try {
            connection = databaseManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_LOGIN);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                userId = null;
            } else {
                do {
                    userId = resultSet.getInt("id");
                } while (resultSet.next());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return userId;
    }

}
