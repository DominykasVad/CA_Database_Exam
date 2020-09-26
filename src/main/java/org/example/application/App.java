package org.example.application;

import org.example.application.loginHandler.LoginHandler;
import org.example.application.menuPrinter.MenuPrinterFactory;
import org.example.application.registrationHandler.RegistrationHandler;
import org.example.model.UserModel;
import org.example.repository.UserRepository;

import java.io.IOException;
import java.util.Scanner;

public class App {

    private Scanner scanner;
    private MenuPrinterFactory menuPrinterFactory;
    private RegistrationHandler registrationHandler;
    private LoginHandler loginHandler;
    private UserRepository userRepository;

    public App() {
        this.scanner = new Scanner(System.in);
        this.menuPrinterFactory = new MenuPrinterFactory();
        try {
            this.registrationHandler = new RegistrationHandler();
            this.loginHandler = new LoginHandler();
            this.userRepository = new UserRepository();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        boolean loggedIn = false;
        boolean actionMenu = false;
        boolean transactionHistoryMenu = false;
        while (!loggedIn) {
            menuPrinterFactory.getMenu("LOGIN").print();
            switch (scanner.nextInt()) {
                case 1: // Register
                    register();
                    break;
                case 2: // login
                    UserModel user = login();
                    if (user != null) {
                        loggedIn = true;
                    }
                    break;
                default:
                    System.out.println("--------------------------------------------------");
                    System.out.println("Bloga ivestis");
                    System.out.println("--------------------------------------------------");
                    break;
            }
        }
        while (loggedIn) {
            switch (scanner.nextInt()) {
                case 1: // deposit
                    break;
                case 2: // withdrawal
                    break;
                case 3: // make transfer
                    break;
                case 4: // export transactions
                    break;
                case 5: // log off
                    loggedIn = false;
                    break;
                default:
                    System.out.println("--------------------------------------------------");
                    System.out.println("Bloga ivestis");
                    System.out.println("--------------------------------------------------");
                    break;
            }
        }
    }

    private UserModel login() {
        UserModel user = null;
        System.out.println("Vartotojo vardas:");
        String username = scanner.next();
        System.out.println("Slaptazodis:");
        String password = scanner.next();
        Integer loginId = loginHandler.handle(username, password);
        if (loginId != null) {
            user = userRepository.getUserById(loginId);
        } else {
            System.out.println("Neteisingas slaptazodis arba vartotojo vardas");
        }
        return user;
    }

    private void register() {
        System.out.println("Vartotojo vardas:");
        String username = scanner.next();
        System.out.println("Slaptazodis:");
        String password = scanner.next();
        System.out.println("Vardas:");
        String name = scanner.next();
        System.out.println("Pavarde:");
        String surname = scanner.next();
        // TODO: 9/27/20 Phone number validation
        System.out.println("Telefono numeris:");
        String phoneNumber = scanner.next();
        // TODO: 9/27/20 Email validation
        System.out.println("Pasto adresas:");
        String emailAddress = scanner.next();

        registrationHandler.handle(username, password, name, surname, phoneNumber, emailAddress);
        System.out.println("Uzregistruota!");
    }

}
