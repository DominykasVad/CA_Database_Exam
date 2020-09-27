package org.example.application.transactionHandler;

import org.example.databaseManager.DatabaseManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.sqlStatementManager.UpdateStatements.UPDATE_ACCOUNT_BALANCE_ADD;

public class DepositHandler {

    private DatabaseManager databaseManager;

    public DepositHandler() throws IOException {
        this.databaseManager = DatabaseManager.getDatabaseManagerInstance();
    }

    public void handle(String accountIban, BigDecimal amount) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = databaseManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE_ADD);
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setString(2, accountIban);
            preparedStatement.executeQuery();

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
    }

}
