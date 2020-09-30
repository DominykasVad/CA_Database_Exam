package org.example.repository;

import org.example.databaseManager.DatabaseManager;
import org.example.model.AccountModel;
import org.example.model.builders.AccountModelBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.sqlStatementManager.SelectStatements.SELECT_ACCOUNT_BY_IBAN;
import static org.example.sqlStatementManager.SelectStatements.SELECT_ACCOUNT_BY_ID;

public class AccountRepository {
    // TODO: 9/26/20 Generify class

    private DatabaseManager databaseManager;

    public AccountRepository() throws IOException {
        this.databaseManager = DatabaseManager.getDatabaseManagerInstance();
    }

    public AccountModel getAccountById(int accountId) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        AccountModel account = null;
        try {
            connection = databaseManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID);
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account = new AccountModelBuilder()
                        .setId(resultSet.getInt("id"))
                        .setIban(resultSet.getString("iban"))
                        .setBalance(resultSet.getBigDecimal("balance"))
                        .createAccountModel();
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
        return account;
    }

    public AccountModel getAccountByIban(String accountIban) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        AccountModel account = null;
        try {
            connection = databaseManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_IBAN);
            preparedStatement.setString(1, accountIban);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account = new AccountModelBuilder()
                        .setId(resultSet.getInt("id"))
                        .setIban(resultSet.getString("iban"))
                        .setBalance(resultSet.getBigDecimal("balance"))
                        .createAccountModel();
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
        return account;
    }
}
