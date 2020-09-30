package org.example.application.menuPrinter;

public class AccountActionMenu implements Printer {
    @Override
    public void print() {
        System.out.println("--------------------------------------------------");
        System.out.println("1. Įnešti pinigų");
        System.out.println("2. Išsiimti pinigus");
        System.out.println("3. Padaryti pavedimą");
        System.out.println("4. Tranzakcijų istoriją");
        System.out.println("5. Išeksportuoti transakcijų istoriją už norimą datą į failą");
        System.out.println("6. Atsijungti");
        System.out.println("--------------------------------------------------");
    }
}
