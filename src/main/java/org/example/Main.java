package org.example;

import java.util.Scanner;

public class Main {
    private Scanner scanner;
    private AuthManager authManager;
    private Wallet wallet;
    private Transaction transaction;
    private DataStorage dataStorage;

    public Main() {
        scanner = new Scanner(System.in);
        authManager = new AuthManager();
        wallet = new Wallet();
        transaction = new Transaction(wallet);
        dataStorage = new DataStorage(authManager, wallet, transaction);
    }

    public void startApplication() {

        dataStorage.readUsersFromFile();

        while (true) {
            showMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegistration();
                    break;
                case 3:
                    dataStorage.safeToFile();
                    exitApp();
                    return;
                default:
                    System.out.println("Неправильный выбор. Попробуйте снова.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Вход");
        System.out.println("2. Регистрация");
        System.out.println("3. Выход");
        System.out.print("Ваш выбор: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода. Пожалуйста, введите число.");
            return -1; // Возвращаем недействительное значение для повторной попытки
        }
    }

    private Double getUserSumChoice() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода. Пожалуйста, введите число.");
            return getUserSumChoice();
        }
    }

    private void handleLogin() {
        System.out.println("Вход:");
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        if (authManager.authenticate(login, password)) {
            System.out.println("Авторизация успешна!");
            System.out.println("Доступ к управлению финансами предоставлен.");
            manageFinances();
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
        }
    }

    private void manageFinances() {

        dataStorage.readIncomesFromFileAndRemoveProcessedLines();
        dataStorage.readExpenseFromFileAndRemoveProcessedLines();
        dataStorage.readBudgetsFromFileAndRemoveProcessedLines();

        boolean exitMenu = true;

        while (exitMenu) {
            System.out.println("\nУправление финансами:");
            System.out.println("1. Внести доход");
            System.out.println("2. Внести расход");
            System.out.println("3. Установить бюджет на категорию");
            System.out.println("0. Выйти на главную страницу");
            System.out.print("Ваш выбор: ");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    System.out.println("Введите категорию дохода:");
                    String category = scanner.nextLine();

                    System.out.print("Внесите сумму дохода: ");
                    double amount = getUserSumChoice();

                    transaction.addIncome(category, amount);
                    transaction.viewIncomes();
                    System.out.println(transaction.getTotalIncome());

                    break;
                case 2:
                    System.out.println("Введите категорию расхода:");
                    String categoryExpenses = scanner.nextLine();

                    System.out.print("Ввести сумму расхода: ");
                    double amountExpenses = getUserSumChoice();

                    transaction.addExpence(categoryExpenses, amountExpenses);
                    transaction.viewExpenses();

                    break;
                case 3:
                    System.out.println("Введите категорию бюджета:");
                    String categoryBudget = scanner.nextLine();

                    System.out.print("Ввести сумму бюджета: ");
                    amount = getUserSumChoice();

                    wallet.setBudget(categoryBudget, amount);
                    wallet.viewBudgets();

                    break;
                case 0:
                    exitMenu = false;
                    break;
                default:
                    System.out.println("Неправильный выбор. Попробуйте снова.");
            }
        }
    }

    private void handleRegistration() {
        System.out.println("Регистрация:");
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        try {
            authManager.addUser(login, password);
            System.out.println("Регистрация прошла успешно!");
        } catch (IllegalArgumentException e) {
            System.out.println("Пользователь с таким именем уже зарегистрирован.");
        }
    }

    private void exitApp() {
        System.out.println("До свидание! Будем рады видеть Вас снова!");
        scanner.close(); // Закрываем сканнер, чтобы избежать утечек ресурсов
        System.exit(0); // Завершаем приложение
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Привет");
        main.startApplication();
    }

    private void displayAllUsers() {
        System.out.println("\nЗарегистрированные пользователи:");
        authManager.displayAllUsers();
    }
}
