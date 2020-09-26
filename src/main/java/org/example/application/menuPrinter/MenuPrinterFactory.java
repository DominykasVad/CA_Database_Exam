package org.example.application.menuPrinter;

public class MenuPrinterFactory {
    public Printer getMenu(String menuType) {
        if (menuType == null) {
            return null;
        }
        if (menuType.equalsIgnoreCase("LOGIN")) {
            return new LoginMenu();

        } else if (menuType.equalsIgnoreCase("ACTION")) {
            return new AccountActionMenu();

        } else if (menuType.equalsIgnoreCase("TRANSACTION_HISTORY")) {
            return new TransactionHistoryMenu();
        }

        return null;
    }
}
