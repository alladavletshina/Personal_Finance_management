package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private AuthManager authManager;
    private Wallet wallet;
    private Transaction transaction;

    public DataStorage(AuthManager authManager, Wallet wallet, Transaction transaction) {
        this.authManager = authManager;
        this.wallet = wallet;
        this.transaction = transaction;
    }

    public void safeToFile() {
        // Имя файла должно быть определено заранее или передаваться в качестве аргумента
        String fileName = "finance_data.txt";

        // Логика для записи данных в файл
        try (FileWriter writer = new FileWriter(fileName, true); // Используем try-with-resources для автоматического закрытия ресурсов
             BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            // Проверяем, существуют ли заголовки в файле
            String firstLine = reader.readLine();
            boolean hasHeaders = firstLine != null && firstLine.startsWith("Пользователь,Пароль,Категория,Сумма,Тип");

            // Запись заголовков, если их нет
            if (!hasHeaders) {
                writer.write("Пользователь,Пароль,Категория,Сумма,Тип\n");
            }

            // Запись данных в файл
            writeTransactions(writer, authManager.getCurrentUser(), authManager.getCurrentPassword(), transaction.getExpenses(), "расход");
            writeTransactions(writer, authManager.getCurrentUser(), authManager.getCurrentPassword(), transaction.getIncomes(), "доход");
            writeTransactions(writer, authManager.getCurrentUser(), authManager.getCurrentPassword(), wallet.getBudgets(), "бюджет");

            System.out.println("Данные записаны в файл " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private void writeTransactions(FileWriter writer, String user, String password, Map<String, Double> transactions, String TypeFlag) throws IOException {
        for (Map.Entry<String, Double> entry : transactions.entrySet()) {
            writer.write(user + "," + password + "," + entry.getKey() + "," + entry.getValue() + "," + TypeFlag + "\n");
        }
    }

    public void readUsersFromFile() {

        String fileName = "finance_data.txt";

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            String line;
            // Пропускаем первую строку с заголовками
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();

                    // Проверяем, существует ли пользователь с таким логином
                    if (!authManager.users.containsKey(username)) {
                        // Добавляем пользователя в коллекцию AuthManager
                        authManager.addUser(username, password);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }


    public void readIncomesFromFileAndRemoveProcessedLines() {
        List<String> linesToKeep = new ArrayList<>();

        String fileName = "finance_data.txt";
        String currentUser = authManager.getCurrentUser();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            String line;
            // Пропускаем первую строку с заголовками
            String header = reader.readLine();
            linesToKeep.add(header);

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String user = parts[0].trim();
                    String TypeTransaction = parts[4].trim();

                    // Проверяем, соответствует ли строка текущему пользователю и является ли это доходом
                    if (user.equals(currentUser) && TypeTransaction.equals("доход")) {
                        String category = parts[2].trim();
                        double amount = Double.parseDouble(parts[3].trim());

                        // Добавляем категорию и доход в коллекцию доходов
                        transaction.addIncome(category, amount);
                    } else {
                        // Сохраняем строку, если она не относится к доходам текущего пользователя
                        linesToKeep.add(line);
                    }
                }
            }
        } catch (IOException e) {
            return;
        }

        // Перезаписываем файл, удалив строки с доходами текущего пользователя
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(fileName), StandardCharsets.UTF_8)) {

            for (String line : linesToKeep) {
                writer.write(line);
                writer.write('\n');
            }

            writer.flush();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public void readExpenseFromFileAndRemoveProcessedLines() {
        List<String> linesToKeep = new ArrayList<>();

        String fileName = "finance_data.txt";
        String currentUser = authManager.getCurrentUser();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            String line;
            // Пропускаем первую строку с заголовками
            String header = reader.readLine();
            linesToKeep.add(header);

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String user = parts[0].trim();
                    String TypeTransaction = parts[4].trim();

                    // Проверяем, соответствует ли строка текущему пользователю и является ли это доходом
                    if (user.equals(currentUser) && TypeTransaction.equals("расход")) {
                        String category = parts[2].trim();
                        double amount = Double.parseDouble(parts[3].trim());

                        // Добавляем категорию и доход в коллекцию доходов
                        transaction.addExpence(category, amount);
                    } else {
                        // Сохраняем строку, если она не относится к доходам текущего пользователя
                        linesToKeep.add(line);
                    }
                }
            }
        } catch (IOException e) {
            return;
        }

        // Перезаписываем файл, удалив строки с доходами текущего пользователя
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(fileName), StandardCharsets.UTF_8)) {

            for (String line : linesToKeep) {
                writer.write(line);
                writer.write('\n');
            }

            writer.flush();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public void readBudgetsFromFileAndRemoveProcessedLines() {
        List<String> linesToKeep = new ArrayList<>();

        String fileName = "finance_data.txt";
        String currentUser = authManager.getCurrentUser();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            String line;
            // Пропускаем первую строку с заголовками
            String header = reader.readLine();
            linesToKeep.add(header);

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String user = parts[0].trim();
                    String TypeTransaction = parts[4].trim();

                    // Проверяем, соответствует ли строка текущему пользователю и является ли это доходом
                    if (user.equals(currentUser) && TypeTransaction.equals("бюджет")) {
                        String category = parts[2].trim();
                        double amount = Double.parseDouble(parts[3].trim());

                        // Добавляем категорию и доход в коллекцию доходов
                        wallet.setBudget(category, amount);
                    } else {
                        // Сохраняем строку, если она не относится к доходам текущего пользователя
                        linesToKeep.add(line);
                    }
                }
            }
        } catch (IOException e) {
            return;
        }

        // Перезаписываем файл, удалив строки с доходами текущего пользователя
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(fileName), StandardCharsets.UTF_8)) {

            for (String line : linesToKeep) {
                writer.write(line);
                writer.write('\n');
            }

            writer.flush();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
