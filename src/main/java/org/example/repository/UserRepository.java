package org.example.repository;

import org.example.databaseManager.DatabaseManager;
import org.example.model.UserModel;
import org.example.model.builders.UserModelBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.sqlStatementManager.SelectStatements.SELECT_USER_BY_ID;

public class UserRepository {

    private DatabaseManager databaseManager;

    public UserRepository() throws IOException {
        this.databaseManager = new DatabaseManager();
    }

    public UserModel getUser(int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        UserModel user = null;
        try {
            connection = databaseManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new UserModelBuilder()
                        .setId(resultSet.getInt("id"))
                        .setUsername(resultSet.getString("username"))
                        .setPassword(resultSet.getString("password"))
                        .setName(resultSet.getString("name"))
                        .setSurname(resultSet.getString("surname"))
                        .setPhoneNumber(resultSet.getString("phone_number"))
                        .setEmailAddress(resultSet.getString("email_address"))
                        .setAccount("account_iban")
                        .createUserModel();
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
        return user;
    }
}