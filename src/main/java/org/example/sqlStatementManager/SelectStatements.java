package org.example.sqlStatementManager;

public class SelectStatements {
    // TODO: 9/26/20 Move statements to .properties files in resources
    public static final String SELECT_USER_LOGIN = "SELECT u.id\n" +
            "FROM Users u\n" +
            "WHERE username = ?\n" +
            "  AND password = ?;\n";

    public static final String SELECT_USER_BY_ID = "SELECT u.id,\n" +
            "       u.username,\n" +
            "       u.password,\n" +
            "       u.name,\n" +
            "       u.surname,\n" +
            "       u.phone_number,\n" +
            "       u.email_address,\n" +
            "       u.account_iban\n" +
            "FROM Users u\n" +
            "WHERE id = ?;\n";

    public static final String SELECT_ACCOUNT_BY_ID = "SELECT a.id, a.iban, a.balance\n" +
            "FROM Accounts a\n" +
            "WHERE id = ?;\n";

    public static final String SELECT_ACCOUNT_BY_IBAN = "SELECT a.id, a.iban, a.balance\n" +
            "FROM Accounts a\n" +
            "WHERE iban = ?;\n";

    public static final String SELECT_TRANSACTION_BY_ID = "SELECT t.id, t.from_account_iban, t.to_account_iban, t.amount, t.date, t.time\n" +
            "FROM Transactions t\n" +
            "WHERE id = ?;";

    public static final String SELECT_TRANSACTION_BY_IBAN = "SELECT t.id, t.from_account_iban, t.to_account_iban, t.amount, t.date, t.time\n" +
            "FROM Transactions t\n" +
            "WHERE to_account_iban = ? OR from_account_iban = ?;\n";
}
