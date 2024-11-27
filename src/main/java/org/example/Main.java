package org.example;

import java.util.Scanner;

public class Main {
    private Scanner scanner;
    private AuthManager authManager;
    private Wallet wallet;

    public Main() {
        scanner = new Scanner(System.in);
        authManager = new AuthManager();
        wallet = new Wallet();
    }

    public void startApplication() {
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

    private void handleLogin() {
        System.out.println("Вход:");
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        User user = new User(login, password);
        if (authManager.authenticate(user)) {
            System.out.println("Авторизация успешна!");
            System.out.println("Доступ к управлению финансами предоставлен.");
            manageFinances();
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
        }
    }

    private void manageFinances() {
        System.out.println("\nУправление финансами:");
        System.out.println("1. Внести доход");
        System.out.println("2. Сделать расход");
        System.out.println("3. Показать текущий баланс");
        System.out.print("Ваш выбор: ");

        int choice = getUserChoice();

        switch (choice) {
            case 1:
                System.out.print("Внесите сумму дохода: ");
                double amount = Double.parseDouble(scanner.nextLine());
                wallet.addIncome(amount);
                break;
            case 2:
                System.out.print("Ввести сумму расхода: ");
                amount = Double.parseDouble(scanner.nextLine());
                System.out.println("нет пока кода");
                break;
            case 3:
                System.out.println("нет пока кода");
                break;
            default:
                System.out.println("Неправильный выбор. Попробуйте снова.");
        }
    }

    private void handleRegistration() {
        System.out.println("Регистрация:");
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        User user = new User(login, password);
        try {
            authManager.addUser(user);
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
