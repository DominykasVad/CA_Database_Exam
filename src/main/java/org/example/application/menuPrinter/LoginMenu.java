package org.example.application.menuPrinter;

public class LoginMenu implements Printer {
    @Override
    public void print() {
        System.out.println("--------------------------------------------------");
        System.out.println("1. Registruotis");
        System.out.println("2. Prisijungti");
        System.out.println("--------------------------------------------------");
    }
}
