package org.example.repository;

import org.example.databaseManager.DatabaseManager;
import org.example.model.TransactionModel;
import org.example.model.builders.TransactionModelBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.sqlStatementManager.SelectStatements.SELECT_TRANSACTION_BY_IBAN;
import static org.example.sqlStatementManager.SelectStatements.SELECT_TRANSACTION_BY_ID;

public class TransactionRepository {

    private DatabaseManager databaseManager;

    public TransactionRepository() throws IOException {
        this.databaseManager = new DatabaseManager();
    }

    public TransactionModel getTransactionById(int transactionId) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        TransactionModel transaction = null;
        try {
            connection = databaseManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_TRANSACTION_BY_ID);
            preparedStatement.setInt(1, transactionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transaction = new TransactionModelBuilder()
                        .setId(resultSet.getInt("id"))
                        .setFromAccount(resultSet.getString("from_account_iban"))
                        .setToAccount(resultSet.getString("to_account_iban"))
                        .setAmount(resultSet.getBigDecimal("amount"))
                        .setDate(resultSet.getDate("date"))
                        .setTime(resultSet.getTime("time"))
                        .createTransactionModel();
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
        return transaction;
    }

    public TransactionModel getTransactionByIban(String transactionIban) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        TransactionModel transaction = null;
        try {
            connection = databaseManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_TRANSACTION_BY_IBAN);
            preparedStatement.setString(1, transactionIban);
            preparedStatement.setString(2, transactionIban);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transaction = new TransactionModelBuilder()
                        .setId(resultSet.getInt("id"))
                        .setFromAccount(resultSet.getString("from_account_iban"))
                        .setToAccount(resultSet.getString("to_account_iban"))
                        .setAmount(resultSet.getBigDecimal("amount"))
                        .setDate(resultSet.getDate("date"))
                        .setTime(resultSet.getTime("time"))
                        .createTransactionModel();
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
        return transaction;
    }
}
