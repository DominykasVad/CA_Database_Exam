package org.example.sqlStatementManager;

public class UpdateStatements {
    public static final String UPDATE_ACCOUNT_BALANCE = "UPDATE Accounts\n" +
            "SET balance = ?\n" +
            "WHERE iban = ?;\n";

    public static final String UPDATE_ACCOUNT_BALANCE_ADD = "UPDATE Accounts\n" +
            "SET balance = balance+?\n" +
            "WHERE iban = ?;\n";

    public static final String UPDATE_ACCOUNT_BALANCE_SUBTRACT = "UPDATE Accounts\n" +
            "SET balance = balance-?\n" +
            "WHERE iban = ?;\n";
}
