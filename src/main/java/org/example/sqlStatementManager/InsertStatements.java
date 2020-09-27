package org.example.sqlStatementManager;

public class InsertStatements {
    public static final String INSERT_NEW_ACCOUNT = "INSERT INTO Accounts (iban, balance)\n" +
            "VALUES (?, ?);\n";

    public static final String INSERT_NEW_USER = "INSERT INTO Users (username, password, name, surname, phone_number, email_address, account_iban)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?);\n";
}
