package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

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
        String fileName = "UsersData.txt";

        // Логика для записи данных в файл
        try (FileWriter writer = new FileWriter(fileName, true)) { // Используем try-with-resources для автоматического закрытия ресурсов
            // Запись данных в файл
            // Запись заголовков для расходов
            writer.write("Категория,Сумма\n");
            for (Map.Entry<String, Double> entry : transaction.getExpenses().entrySet()) {
                writer.write(this.user + "," + entry.getKey() + "," + entry.getValue() + "\n");
            }

            // Разделитель между расходами и доходами
            writer.write("\n");

            // Запись заголовков для доходов
            writer.write("Категория,Сумма\n");
            for (Map.Entry<String, Double> entry : transaction.getIncomes().entrySet()) {
                writer.write(this.user + "," + entry.getKey() + "," + entry.getValue() + "\n");
            }

            System.out.println("Данные записаны в файл " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
