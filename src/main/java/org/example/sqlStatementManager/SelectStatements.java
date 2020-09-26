package org.example.sqlStatementManager;

public class SelectStatements {
    // TODO: 9/26/20 Move statements to .sql files in resources
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

}
