package org.example;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    private final Scanner scanner;
    private final AuthManager authManager;
    private final Wallet wallet;
    private final Transaction transaction;
    private final DataStorage dataStorage;
    private final NotificationManager notificationManager;

    public Main() {
        scanner = new Scanner(System.in);
        authManager = new AuthManager();
        wallet = new Wallet(authManager);
        transaction = new Transaction(wallet);
        dataStorage = new DataStorage(authManager, wallet, transaction);
        notificationManager = new NotificationManager(transaction, wallet);
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

        while (true) {
            System.out.println("\nУправление финансами:");
            System.out.println("1. Внести доход");
            System.out.println("2. Внести расход");
            System.out.println("3. Установить бюджет на категорию");
            System.out.println("4. Выгрузить финансовый отчет в файл");
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    System.out.println("Введите категорию дохода:");
                    String category = scanner.nextLine();

                    System.out.print("Внесите сумму дохода: ");
                    double amount = getUserSumChoice();

                    transaction.addIncome(category, amount);
                    break;
                case 2:
                    System.out.println("Введите категорию расхода:");
                    String categoryExpenses = scanner.nextLine();

                    System.out.print("Ввести сумму расхода: ");
                    double amountExpenses = getUserSumChoice();

                    transaction.addExpence(categoryExpenses, amountExpenses);
                    notificationManager.checkExceedingExpenses();
                    notificationManager.checkBudgetLimitExceeded();
                    break;
                case 3:
                    System.out.println("Введите категорию бюджета:");
                    String categoryBudget = scanner.nextLine();

                    System.out.print("Ввести сумму бюджета: ");
                    amount = getUserSumChoice();

                    wallet.setBudget(categoryBudget, amount);
                    break;
                case 4:
                    saveDisplayResultsToFile();
                    break;
                case 0:
                    dataStorage.safeToFile();
                    displayAllTransactions();
                    exitApp();
                    return;
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
        System.out.println();
        System.out.println("До свидание! Будем рады видеть Вас снова!");
        scanner.close(); // Закрываем сканнер, чтобы избежать утечек ресурсов
        System.exit(0); // Завершаем приложение
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Привет");
        main.startApplication();
    }

    public void displayAllTransactions() {
        System.out.println();
        System.out.println("Общий доход: " + transaction.getTotalIncome());
        System.out.println();
        transaction.viewIncomes();
        System.out.println();
        System.out.println("Общие расходы: " + transaction.getTotalExpense());
        System.out.println();
        transaction.viewExpenses();
        System.out.println();
        transaction.viewBudgets();
    }

    public void saveDisplayResultsToFile() {

        String fileName = "Финансовый отчет.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Общий доход: " + transaction.getTotalIncome());
            writer.println();
            viewIncomes(writer);
            writer.println();
            writer.println("Общие расходы: " + transaction.getTotalExpense());
            writer.println();
            viewExpenses(writer);
            writer.println();
            viewBudgets(writer);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении данных в файл: " + e.getMessage());
        }

        System.out.println("Данные записаны в файл " + fileName);
    }

    // Методы viewIncomes, viewExpenses и viewBudgets нужно немного изменить, чтобы они принимали PrintWriter
    public void viewIncomes(PrintWriter writer) {
        if (transaction.incomes.isEmpty()) {
            writer.println("Нет записей о доходах.");
        } else {
            writer.println("Доходы по категориям:");
            for (String category : transaction.incomes.keySet()) {
                final double amount = transaction.incomes.getOrDefault(category, 0.0);
                writer.printf("%s: %.2f%n", category, amount);
            }
        }
    }

    public void viewExpenses(PrintWriter writer) {
        if (transaction.expenses.isEmpty()) {
            writer.println("Нет записей о расходах.");
        } else {
            writer.println("Расходы по категориям:");
            for (String category : transaction.expenses.keySet()) {
                final double amount = transaction.expenses.getOrDefault(category, 0.0);
                writer.printf("%s: %.2f%n", category, amount);
            }
        }
    }

    public void viewBudgets(PrintWriter writer) {
        if (wallet.budgets.isEmpty()) {
            writer.println("Нет записей о бюджетах.");
        } else {
            writer.println("Бюджеты по категориям:");
            for (String category : wallet.budgets.keySet()) {
                final double startBudget = wallet.budgets.getOrDefault(category, 0.0);
                double spentAmount = transaction.getSpentAmount(category);
                double remainingBudget = startBudget - spentAmount;

                if (spentAmount == 0) {
                    writer.printf("%s: %.2f, оставшийся бюджет: %.2f (расходы отсутствуют)%n", category, startBudget, remainingBudget);
                } else {
                    writer.printf("%s: %.2f, оставшийся бюджет: %.2f%n", category, startBudget, remainingBudget);
                }
            }
        }
    }
}
