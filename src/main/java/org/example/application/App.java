package org.example.application;

import net.steppschuh.markdowngenerator.table.Table;
import org.example.application.loginHandler.LoginHandler;
import org.example.application.menuPrinter.MenuPrinterFactory;
import org.example.application.registrationHandler.RegistrationHandler;
import org.example.application.transactionExportHandler.TransactionExportHandler;
import org.example.application.transactionHandler.DepositHandler;
import org.example.application.transactionHandler.NewTransactionHandler;
import org.example.application.transactionHandler.WithdrawalHandler;
import org.example.model.AccountModel;
import org.example.model.TransactionModel;
import org.example.model.UserModel;
import org.example.repository.AccountRepository;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    // TODO: 9/27/20 Clean up this mess

    private Scanner scanner;
    private MenuPrinterFactory menuPrinterFactory;
    private RegistrationHandler registrationHandler;
    private LoginHandler loginHandler;
    private DepositHandler depositHandler;
    private NewTransactionHandler newTransactionHandler;
    private WithdrawalHandler withdrawalHandler;
    private TransactionExportHandler transactionExportHandler;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public App() {
        this.scanner = new Scanner(System.in);
        this.menuPrinterFactory = new MenuPrinterFactory();
        try {
            this.registrationHandler = new RegistrationHandler();
            this.loginHandler = new LoginHandler();
            this.depositHandler = new DepositHandler();
            this.newTransactionHandler = new NewTransactionHandler();
            this.withdrawalHandler = new WithdrawalHandler();
            this.transactionExportHandler = new TransactionExportHandler();
            this.userRepository = new UserRepository();
            this.accountRepository = new AccountRepository();
            this.transactionRepository = new TransactionRepository();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        boolean loggedIn = false;
        UserModel user = null;
        AccountModel account;
        while (!loggedIn) {
            menuPrinterFactory.getMenu("LOGIN").print();
            switch (scanner.nextInt()) {
                case 1: // Register
                    register();
                    break;
                case 2: // login
                    // TODO: 9/27/20 Check if user table is empty
                    user = login();
                    account = accountRepository.getAccountByIban(user.getAccount());
                    assert user.getAccount().equals(account.getIban());
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
            account = accountRepository.getAccountByIban(user.getAccount());
            System.out.println("--------------------------------------------------");
            System.out.printf("Vartotojo ID: %s%n", user.getUsername());
            System.out.printf("Vardas: %s%n", user.getName());
            System.out.printf("Pavarde: %s%n", user.getSurname());
            System.out.printf("Saskaitos IBAN: %s%n", user.getAccount());
            System.out.printf("Balansas: %s%n", account.getBalance());
            menuPrinterFactory.getMenu("ACTION").print();
            switch (scanner.nextInt()) {
                case 1: // deposit
                    deposit(account);
                    break;
                case 2: // withdrawal
                    withdrawal(account);
                    break;
                case 3: // make transfer
                    transfer(account);
                    break;
                case 4:
                    transactionHistory(user.getAccount());
                    break;
                case 5: // export transactions
                    try {
                        exportTransactions(user.getAccount());
                    } catch (ParseException e) {
                        System.out.println("Bloga ivestis");
                    }
                    break;
                case 6: // log off
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

    private void transactionHistory(String accountIban) {
        List<TransactionModel> transactionList = transactionRepository.getTransactionByIbanList(accountIban);
        System.out.println("--------------------------------------------------");
        System.out.println("Tranzakciju istorija");
        System.out.println("--------------------------------------------------");
        Table.Builder tableBuilderSent = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT)
                .addRow("ID", "Kam siusta", "Suma", "Data", "Laikas");
        transactionList.stream()
                .filter(f -> f.getFromAccount().equals(accountIban))
                .forEach(t -> tableBuilderSent.addRow(t.getId(), t.getToAccount(), t.getAmount(), t.getDate(), t.getTime()));
        System.out.println(tableBuilderSent.build());
        System.out.println("--------------------------------------------------");
        Table.Builder tableBuilderReceived = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT)
                .addRow("ID", "Is ko gauta", "Suma", "Data", "Laikas");
        transactionList.stream()
                .filter(f -> f.getToAccount().equals(accountIban))
                .forEach(t -> tableBuilderReceived.addRow(t.getId(), t.getFromAccount(), t.getAmount(), t.getDate(), t.getTime()));
        System.out.println(tableBuilderReceived.build());
        promptEnterKey();

    }

    private void exportTransactions(String accountIban) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Ivesti data nuo: (yyyy-MM-dd)");
        String enterDateFrom = scanner.next();
        System.out.println("Ivesti data iki: (yyyy-MM-dd)");
        String enterDateTo = scanner.next();
        Date parseDateFrom = dateFormat.parse(enterDateFrom);
        Date parseDateTo = dateFormat.parse(enterDateTo);
        java.sql.Date setDateFrom = new java.sql.Date(parseDateFrom.getTime());
        java.sql.Date setDateTo = new java.sql.Date(parseDateTo.getTime());

        List<TransactionModel> transactionList = transactionRepository.getTransactionByIbanList(accountIban);
        List<TransactionModel> sortedTransactionList = transactionList.stream()
                .filter(t -> t.getDate().after(setDateFrom) & t.getDate().before(setDateTo))
                .collect(Collectors.toList());
        transactionExportHandler.handle(sortedTransactionList);


    }

    private void transfer(AccountModel account) {
        String receiverIban;
        System.out.println("--------------------------------------------------");
        System.out.println("Siusti pinigus");
        System.out.println("--------------------------------------------------");
        System.out.println("Suma:");
        BigDecimal amount = getAmountToSend(account.getBalance());
        System.out.println("Gavejo saskaitos IBAN");
        receiverIban = scanner.next();
        // TODO: 9/27/20 IBAN validation
        newTransactionHandler.handle(receiverIban, account.getIban(), amount);
        System.out.println("Pervesta!");
        promptEnterKey();

    }

    private void withdrawal(AccountModel account) {
        System.out.println("--------------------------------------------------");
        System.out.println("Isimti pinigus");
        System.out.println("--------------------------------------------------");
        System.out.println("Suma:");
        BigDecimal amount = getAmountToSend(account.getBalance());
        withdrawalHandler.handle(account.getIban(), account.getBalance().subtract(amount));
    }

    private BigDecimal getAmountToSend(BigDecimal currentBalance) {
        BigDecimal amount;
            do {
                amount = new BigDecimal(scanner.next());
                if (compareBalanceAmount(currentBalance, amount)) {
                    System.out.println("Nepakanka pinigu");
                    promptEnterKey();
                }
            } while (compareBalanceAmount(currentBalance, amount));
        return amount;
    }

    private void deposit(AccountModel account) {
        BigDecimal amount;
        System.out.println("--------------------------------------------------");
        System.out.println("Inesti pinigus");
        System.out.println("--------------------------------------------------");
        System.out.println("Suma:");
        amount = new BigDecimal(scanner.next());
        depositHandler.handle(account.getIban(), amount);
        System.out.println("Atlikta");
        promptEnterKey();
    }

    private UserModel login() {
        UserModel user = null;
        String username;
        String password;
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Vartotojo vardas:");
            username = scanner.next();
            System.out.println("Slaptazodis:");
            password = scanner.next();
            Integer loginId = loginHandler.handle(username, password);
            if (loginId != null) {
                user = userRepository.getUserById(loginId);
                loggedIn = true;
            } else {
                System.out.println("Neteisingas slaptazodis arba vartotojo vardas");
            }
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

    /**
     * @param baseAmount base amount of account
     * @param comparedAmount amount to deposit or send
     * @return true if theres not enough money in account
     */
    private boolean compareBalanceAmount(BigDecimal baseAmount, BigDecimal comparedAmount) {
        //noinspection ComparatorResultComparison
        return baseAmount.compareTo(comparedAmount) == -1;
    }

    private static void promptEnterKey() {
        System.out.println("Spausk ENTER testi");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}
