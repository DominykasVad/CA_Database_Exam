package org.example.application.menuPrinter;

public class TransactionHistoryMenu implements Printer {
    @Override
    public void print() {
        System.out.println("--------------------------------------------------");
        System.out.println("1. Perziureti");
        System.out.println("2. Issaugoti");
        System.out.println("3. Iseiti");
        System.out.println("--------------------------------------------------");
    }
}
