package org.example.application.registrationHandler;

import org.example.databaseManager.DatabaseManager;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.sqlStatementManager.InsertStatements.INSERT_NEW_ACCOUNT;
import static org.example.sqlStatementManager.InsertStatements.INSERT_NEW_USER;

public class RegistrationHandler {
    private DatabaseManager databaseManager;

    public RegistrationHandler() throws IOException {
        this.databaseManager = DatabaseManager.getDatabaseManagerInstance();
    }

    public Integer handle(String username, String password, String name, String surname, String phoneNumber, String emailAddress) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        Integer userId = null;
        try {
            connection = databaseManager.getConnection();
            connection.setAutoCommit(false);
            String accountIban = newIbanGenerator("73000", CountryCode.LT);
            preparedStatement = connection.prepareStatement(INSERT_NEW_ACCOUNT);
            preparedStatement.setString(1, accountIban);
            preparedStatement.setBigDecimal(2, new BigDecimal("0.0"));
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement(INSERT_NEW_USER);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, surname);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, emailAddress);
            preparedStatement.setString(7, accountIban);
            preparedStatement.executeQuery();

        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return userId;
    }

    private String newIbanGenerator(String bankCode, CountryCode countryCode) {
        Iban iban = new Iban.Builder()
                .countryCode(countryCode)
                .bankCode(bankCode)
                .buildRandom();
        return iban.toString();
    }

}
