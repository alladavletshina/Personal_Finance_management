package org.example;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {
    AuthManager authManager = new AuthManager(); // Инициализация менеджера авторизаций
    DataStorage dataStorage = new DataStorage();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Main main = new Main();
        System.out.println("Привет");

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Вход");
            System.out.println("2. Регистрация");
            System.out.println("3. Выход");

            System.out.print("Ваш выбор: ");
            Scanner scanner = new Scanner(System.in); // Для считывания ввода пользователя

            int choice = Integer.parseInt(scanner.nextLine()); // Получаем выбор пользователя

            switch (choice) {
                case 1:
                    System.out.println("Вышла");
                    break;
                case 2:
                    main.handleRegistration();
                    break;
                case 3:
                    main.exitApp();
                default:
                    System.out.println("Неправильный выбор. Попробуйте снова.");
            }

        }

    }

    private void exitApp() {
        Scanner scanner = new Scanner(System.in);
        try {
            if (scanner != null) {
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("До встречи!");
            System.exit(0);
        }
    }

    private void handleRegistration() {

        Scanner scanner = new Scanner(System.in);

        // Логика для регистрации нового пользователя

        System.out.println("Регистрация пользователя началась.");
        System.out.println("Запишите свои данные:");

        System.out.println("Имя пользователя:");
        String username = scanner.nextLine();

        System.out.println("Пароль пользователя:");
        String password = scanner.nextLine();

        User user = new User(username, password);
        authManager.addUser(user);

        System.out.println("Регистрация пользователя завершена успешно.");
        System.out.println("Вы можете начинать пользоваться услугами нашего портала.");

        authManager.displayAllUsers();
    }

}
