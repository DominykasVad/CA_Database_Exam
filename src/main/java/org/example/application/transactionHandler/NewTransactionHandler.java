package org.example.application.transactionHandler;

import org.example.databaseManager.DatabaseManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

import static org.example.sqlStatementManager.InsertStatements.INSERT_NEW_TRANSACTION;
import static org.example.sqlStatementManager.UpdateStatements.*;

public class NewTransactionHandler {

    private DatabaseManager databaseManager;

    public NewTransactionHandler() throws IOException {
        this.databaseManager = DatabaseManager.getDatabaseManagerInstance();
    }

    public void handle(String toAccountIban, String fromAccountIban, BigDecimal amount) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        Date date = Date.valueOf(LocalDate.now());
        Time time = new Time(date.getTime());
        try {
            connection = databaseManager.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT_NEW_TRANSACTION);
            preparedStatement.setString(1, fromAccountIban);
            preparedStatement.setString(2, toAccountIban);
            preparedStatement.setBigDecimal(3, amount);
            preparedStatement.setDate(4, date);
            preparedStatement.setTime(5, time);
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE_SUBTRACT);
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setString(2, fromAccountIban);
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE_ADD);
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setString(2, toAccountIban);
            preparedStatement.executeQuery();
            connection.commit();

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
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
