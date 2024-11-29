package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DataStorage {
    private AuthManager authManager;
    private Wallet wallet;
    private Transaction transaction;
    private User user;

    public DataStorage(AuthManager authManager, Wallet wallet, Transaction transaction) {
        this.authManager = authManager;
        this.wallet = wallet;
        this.transaction = transaction;
        this.user = user;
    }

    public void safeToFile() {
        // Имя файла должно быть определено заранее или передаваться в качестве аргумента
        String fileName = "finance_data.txt";

        // Логика для записи данных в файл
        try (FileWriter writer = new FileWriter(fileName, true); // Используем try-with-resources для автоматического закрытия ресурсов
             BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            // Проверяем, существуют ли заголовки в файле
            String firstLine = reader.readLine();
            boolean hasHeaders = firstLine != null && firstLine.startsWith("Пользователь,Категория,Сумма,Расход?");

            // Запись заголовков, если их нет
            if (!hasHeaders) {
                writer.write("Пользователь,Категория,Сумма,Расход?\n");
            }

            // Запись данных в файл
            writeTransactions(writer, authManager.getCurrentUser(), transaction.getExpenses(), "да");
            writeTransactions(writer, authManager.getCurrentUser(), transaction.getIncomes(), "нет");

            System.out.println("Данные записаны в файл " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private void writeTransactions(FileWriter writer, String user, Map<String, Double> transactions, String expenseFlag) throws IOException {
        for (Map.Entry<String, Double> entry : transactions.entrySet()) {
            writer.write(user + "," + entry.getKey() + "," + entry.getValue() + "," + expenseFlag + "\n");
        }
    }

}
